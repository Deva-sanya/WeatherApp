package com.example.demo.controller;

import com.example.demo.model.Weather;
import com.example.demo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.NotActiveException;
import java.util.List;
import java.util.Map;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    private String url = "http://api.weatherapi.com/v1/current.json?key=267da6ee01ec4e0a95a162121232003&q=Minsk&aqi=no";

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

    @PostMapping(value = "/addWeather")
    public Weather newWeather(@RequestBody Weather weather) {
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
