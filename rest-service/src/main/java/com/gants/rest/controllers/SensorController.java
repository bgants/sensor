package com.gants.rest.controllers;

import com.gants.model.AggregateResults;
import com.gants.model.Sensor;
import com.gants.repository.SensorRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(path = "sensor", produces = "application/json")
@CrossOrigin(origins = "*")
@ComponentScan(basePackages = { "com.gants.model.*" })
@EntityScan("com.gants.model")
@EnableJpaRepositories(basePackages = "com.gants.repository")
@Slf4j
public class SensorController {
    Logger logger = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    SensorRepository sensorRepository;

    @GetMapping("/readings")
    public Iterable<Sensor> getReadings() {
        return sensorRepository.findAll();
    }

    @GetMapping("/readings/sampleDates")
    public List<Date> getBySensorDate() {
        return sensorRepository.findDistinctReadingSampleDates();
    }

    @GetMapping("readings/avgTempByDay")
    public List<AggregateResults> getAvgTempByDay() {
        return sensorRepository.findAvgTempByDay();
    }

    @PostMapping(path = "/reading")
    @ResponseStatus(HttpStatus.CREATED)
    public Sensor processSensorReading(@RequestBody Sensor sensor) {
        return sensorRepository.save(sensor);
    }}
