package com.example.demo.controller;

import com.example.demo.model.Weather;
import com.example.demo.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.NotActiveException;
import java.util.List;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    private static final ObjectMapper mapper = new ObjectMapper();

    private String url = "http://api.weatherapi.com/v1/current.json?key=267da6ee01ec4e0a95a162121232003&q=Minsk&aqi=no";

    private double temp_c;
    private String location;
    private double wind_mph;
    private double pressure_mb;
    private int humidity;
    private String last_updated;
    private String condition;


    @GetMapping("/hello")
    public String hello() {
        return "hello from controller";
    }

    @GetMapping("/allWeathers")
    public List<Weather> getAllWeathers() {
        return weatherService.findAllWeather();
    }

    @GetMapping("/{last_updated}")
    public Weather getWeatherOnCurrentDate(@PathVariable("last_updated") String last_updated) throws NotActiveException {
        List<Weather> weathers = weatherService.findAllWeather();
        return weathers.stream().filter(weather -> weather.getLast_updated().equals(last_updated)).findFirst().orElseThrow(NotActiveException::new);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

        return args -> {
            RestTemplate restTemplate = new RestTemplate();
            String resp = restTemplate.getForObject(url, String.class);

            JsonNode root = mapper.readTree(resp);

            JsonNode locationNode = root.path("location");
            if (!locationNode.isMissingNode()) {
                location = locationNode.path("region").asText();

                System.out.println("name : " + locationNode.path("name").asText());
                System.out.println("region : " + locationNode.path("region").asText());
            }

            JsonNode currentNode = root.path("current");
            if (!currentNode.isMissingNode()) {
                temp_c = currentNode.path("temp_c").asDouble();
                wind_mph = currentNode.path("wind_mph").asDouble();
                pressure_mb = currentNode.path("pressure_mb").asDouble();
                humidity = currentNode.path("humidity").asInt();
                last_updated = currentNode.path("last_updated").asText();

                System.out.println("temperature in celsius: " + currentNode.path("temp_c").asText());
                System.out.println("wind mph: " + currentNode.path("wind_mph").asText());
                System.out.println("pressure_mb: " + currentNode.path("pressure_mb").asText());
                System.out.println("humidity: " + currentNode.path("humidity").asText());
                System.out.println("last_updated: " + currentNode.path("last_updated").asText());
            }

            JsonNode conditionNode = root.path("condition");
            if (!conditionNode.isMissingNode()) {
                System.out.println("condition: " + conditionNode.path("text").asText());
            }
        };
    }

    @PostMapping(value = "/addWeather")
    public Weather newWeather(@RequestBody Weather weather) {
        weather.setTemp_c((float) temp_c);
        weather.setWind_mph((float) wind_mph);
        weather.setPressure_mb((float) pressure_mb);
        weather.setHumidity(humidity);
        weather.setLocation(location);
        weather.setLast_updated(last_updated);

        return weatherService.addWeatherOnCurrentDate(weather);
    }

}
