package com.example.demo.repository;

import com.example.demo.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    //Weather findWeatherByLast_Updated(String last_updated);
}
