package com.example.demo.service;


import com.example.demo.model.Weather;
import com.example.demo.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Component
public class WeatherServiceImplementation implements WeatherService {
    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    public Weather addWeatherOnCurrentDate(Weather weather) {
        Weather weatherOnCurrentDate = weatherRepository.saveAndFlush(weather);
        return weatherOnCurrentDate;
    }

    @Override
    public Weather findWeatherByCurrentDate(String last_updated) {
        return weatherRepository.findWeatherByCurrentDate(last_updated);
    }

    /*@Override
    public Weather getWeatherFromPeriod(String previousDate, String currentDate) {
        return weatherRepository.findWeatherByCurrentDate(currentDate);
    }*/
}
