package com.gants.aggregator.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gants.model.Sensor;
import com.gants.repository.SensorRepository;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.time.Instant;

@Configuration
@ComponentScan(basePackages = { "com.gants.model.*" })
@EntityScan("com.gants.model")
@EnableJpaRepositories(basePackages = "com.gants.repository")
public class AppConfig {
    Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Autowired
    SensorRepository sensorRepository;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        logger.info("In MqttPahoClientFactory method of AppConfig");
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] { "tcp://tethys:1883"});
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("tcp://tethys:1883", "testClient",
                        "topic/temp-humid");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Autowired
            SensorRepository sensorRepository;

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println(message.getPayload());
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Sensor sensor = mapper.readValue(message.getPayload().toString(), Sensor.class);
                    sensor.setSensorSampleTime(Instant.now());
                    logger.info(sensor.toString());
                    sensorRepository.save(sensor);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}