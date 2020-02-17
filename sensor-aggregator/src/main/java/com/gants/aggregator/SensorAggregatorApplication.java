package com.gants.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.gants")
public class SensorAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorAggregatorApplication.class, args);
	}

}
