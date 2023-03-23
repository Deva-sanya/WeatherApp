package com.example.demo.controller;

import com.example.demo.model.Weather;
import com.example.demo.repository.WeatherRepository;
import com.example.demo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/weather")

public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    private String url = "http://api.weatherapi.com/v1/current.json?key=267da6ee01ec4e0a95a162121232003&q=Minsk&aqi=no";


    @GetMapping(value = "/weather/{last_updated}")
    public Weather getWeatherOnCurrentDate(@PathVariable String last_updated) {
        System.out.println("hello from getWeatherOnCurrentDate");
        return weatherService.findWeatherByCurrentDate(last_updated);
    }

    @PostMapping("/weatherAdd")
    public Weather newWeather(@RequestBody Weather weather) {
        float pressure_mb = weather.getPressure_mb();
        float wind_mph = weather.getWind_mph();
        float temp_c = weather.getTemp_c();
        int humidity = weather.getHumidity();
        String condition = weather.getCondition();
        String location = weather.getLocation();
        String last_updated = weather.getLast_updated();

        weather.setPressure_mb(pressure_mb);
        weather.setWind_mph(wind_mph);
        weather.setTemp_c(temp_c);
        weather.setHumidity(humidity);
        weather.setCondition(condition);
        weather.setLocation(location);
        weather.setLast_updated(last_updated);

        System.out.println("hello from add weather");
        return weatherService.addWeatherOnCurrentDate(weather);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            RestTemplate restTemplate = new RestTemplate();
            String resp = restTemplate.getForObject(url, String.class);
            JsonParser springParser = JsonParserFactory.getJsonParser();

            Map<String, Object> map = springParser.parseMap(resp);
            String mapArray[] = new String[map.size()];
            System.out.println("Items found: " + mapArray.length);

            int i = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
                i++;
            }
            System.out.println(map.get("location"));
        };
    }

}
