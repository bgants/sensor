package com.gants.repository;

import com.gants.model.AggregateResults;
import com.gants.model.Sensor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface SensorRepository extends CrudRepository<Sensor, Long> {

    @Query(
            value = "SELECT DISTINCT CAST(sensor_sample_time AS date) FROM sensor ORDER BY sensor_sample_time ASC",
            nativeQuery = true
    )
    List<Date> findDistinctReadingSampleDates();

    @Query(
            value = "SELECT CAST(sensor_sample_time as date) aggregateDate, AVG(temperature) aggregateValue FROM sensor " +
                    "GROUP BY CAST(sensor_sample_time AS date) ORDER BY aggregateDate ASC",
            nativeQuery = true
    )
    List<AggregateResults> findAvgTempByDay();
}