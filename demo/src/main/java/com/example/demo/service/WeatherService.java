package com.example.demo.service;
import com.example.demo.model.Weather;

public interface WeatherService {

    Weather addWeatherOnCurrentDate(Weather weather);
    Weather findWeatherByCurrentDate(String last_updated);
    //Weather getWeatherFromPeriod(String previousDate, String last_update);
}
