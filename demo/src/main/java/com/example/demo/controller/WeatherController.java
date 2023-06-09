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

    private static final ObjectMapper mapper = new ObjectMapper();

    private String url = "http://api.weatherapi.com/v1/current.json?key=267da6ee01ec4e0a95a162121232003&q=Minsk&aqi=no";

    private double temp_c;
    private String location;
    private double wind_mph;
    private double pressure_mb;
    private int humidity;
    private String last;
    private String condition;


    @GetMapping("/hello")
    public String hello() {
        return "hello from controller";
    }


    @GetMapping("/{last}")
    public Weather getWeatherOnCurrentDateByLast_Updated(@PathVariable("last") String last) {
        System.out.println("from last_updated");
        return weatherService.findWeatherByLast(last);
    }

    @PostMapping(consumes = "application/json", value = "/addWeather")
    @ResponseStatus(HttpStatus.CREATED)
    public Weather addNewWeather(@RequestBody Weather weather) {
        Weather weather1 = new Weather();

        weather1.setLocation(location);
        weather1.setHumidity(humidity);
        weather1.setPressure_mb(Math.ceil(pressure_mb));
        weather1.setWind_mph(Math.ceil(wind_mph));
        weather1.setTemp_c(Math.ceil(temp_c));
        weather1.setCondition(condition);
        weather1.setLast(last);

        return weatherService.addWeatherOnCurrentDate(weather1);
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
                last = currentNode.path("last_updated").asText();
                condition = currentNode.path("condition").path("text").asText();

                System.out.println("temperature in celsius: " + currentNode.path("temp_c").asText());
                System.out.println("wind mph: " + currentNode.path("wind_mph").asText());
                System.out.println("pressure_mb: " + currentNode.path("pressure_mb").asText());
                System.out.println("humidity: " + currentNode.path("humidity").asText());
                System.out.println("last updated: " + currentNode.path("last_updated").asText());
                System.out.println("condition: " + currentNode.path("condition").path("text").asText());

            }

        };
    }
}