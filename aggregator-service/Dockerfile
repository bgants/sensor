FROM openjdk:8-jdk-alpine
COPY ./target/sensor-aggregator-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java","-jar","sensor-aggregator-0.0.1-SNAPSHOT.jar"]