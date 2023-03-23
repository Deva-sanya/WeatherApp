package com.example.demo.service;
import com.example.demo.model.Weather;

import java.util.List;

public interface WeatherService {

    Weather addWeatherOnCurrentDate(Weather weather);
    /*Weather getWeatherFromPeriod(String previousDate, String last_update);*/
    List<Weather> findAllWeather();
}
