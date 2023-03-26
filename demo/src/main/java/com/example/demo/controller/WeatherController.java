package com.example.demo.controller;

import com.example.demo.model.Weather;
import com.example.demo.repository.WeatherRepository;
import com.example.demo.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private WeatherRepository weatherRepository;

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

    @GetMapping("/{id}")
    public Weather getWeatherOnCurrentDateById(@PathVariable("id") int id) {
        return weatherService.findWeatherById(id);
    }

    /*@GetMapping("/{last_updated}")
    public Weather getWeatherOnCurrentDate(@PathVariable("last_updated") Date last_updated) {
        return weatherRepository.getWeatherByConditionEqualsAndLast_updated(last_updated);
    }*/

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
                System.out.println("last updated: " + currentNode.path("last_updated").asText());

            }

            JsonNode conditionNode = root.path("condition");
            if (!conditionNode.isMissingNode()) {
                condition = currentNode.path("condition").asText();
                System.out.println("condition: " + conditionNode.path("text").asText());
            }
        };
    }

    @PostMapping(consumes = "application/json", value = "/addWeather")
    @ResponseStatus(HttpStatus.CREATED)
    public Weather addNewWeather(@RequestBody Weather weather) {
        Weather weather1 = new Weather();
        weather1.setLocation(location);
        weather1.setHumidity(humidity);
        weather1.setPressure_mb((float) Math.ceil(pressure_mb));
        weather1.setWind_mph((float) Math.ceil(wind_mph));
        weather1.setTemp_c((float) Math.ceil(temp_c));
        weather1.setCondition(condition);
        weather1.setLast_updated(last_updated);

        weatherRepository.save(weather1);

        return weatherService.addWeatherOnCurrentDate(weather1);
    }

}
