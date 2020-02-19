FROM openjdk:8-jdk-alpine
COPY ./target/sensor-rest-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java","-jar","sensor-rest-0.0.1-SNAPSHOT.jar"]