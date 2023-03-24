package com.example.demo.repository;

import com.example.demo.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    //Weather getWeatherByConditionEqualsAndLast_updated(Date last_updated);
}
