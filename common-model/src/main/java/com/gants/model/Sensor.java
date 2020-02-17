package com.gants.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Data
@JsonIgnoreProperties({"id", "sensorSampleTime"})
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String sensorModel;
    private double temperature;
    private double humidity;
    private int sensorGPIONum;
    private Instant sensorSampleTime;
    private String sensorLocation;
    private String sensorName;
}


