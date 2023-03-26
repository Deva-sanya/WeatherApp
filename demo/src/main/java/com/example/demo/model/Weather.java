package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "weather")
public class Weather {

    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "id", length = 6, nullable = false)
    private int id;
    @Column(name = "pressure_mb")
    private float pressure_mb;
    @Column(name = "wind_mph")
    private float wind_mph;
    @Column(name = "temperature_celsius")
    private float temp_c;
    @Column(name = "humidity")
    private int humidity;
    @Column(name = "condition")
    private String condition;
    @Column(name = "location")
    private String location;
    @Column(name = "last_updated")
    private String last_updated;

    private String previousDate;

    public Weather() {

    }
}
