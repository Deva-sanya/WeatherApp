package com.example.demo.service;


import com.example.demo.model.Weather;
import com.example.demo.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Component
public class WeatherServiceImplementation implements WeatherService {
    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    public List<Weather> findAllWeather() {
        return weatherRepository.findAll();
    }

    @Override
    public Weather addWeatherOnCurrentDate(Weather weather) {
       return weatherRepository.save(weather);
    }

    /*
    @Override
    public Weather getWeatherFromPeriod(String previousDate, String currentDate) {
        return weatherRepository.findWeatherByCurrentDate(currentDate);
    }*/
}
