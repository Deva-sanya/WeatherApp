package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.*;

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
    private double pressure_mb;
    @Column(name = "wind_mph")
    private double wind_mph;
    @Column(name = "temperature_celsius")
    private double temp_c;
    @Column(name = "humidity")
    private int humidity;
    @Column(name = "condition")
    private String condition;
    @Column(name = "location")
    private String location;
    @Column(name = "last")
    private String last;

    public Weather() {

    }
}