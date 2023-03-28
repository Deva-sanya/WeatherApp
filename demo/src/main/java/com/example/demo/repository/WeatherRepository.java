package com.example.demo.repository;

import com.example.demo.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    Weather findWeatherByLast(String last);
}

