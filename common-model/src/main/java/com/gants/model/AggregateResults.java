package com.gants.model;
/* This is a A projection interface to retrieve a subset of attributes.
worth some time --> https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
 */
public interface AggregateResults {
    double getAggregateValue();
    String getAggregateDate();
}