package com.logicore.rest.services.servicevalidation.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Bean
    public NewTopic libraryEvents() {
        return TopicBuilder.name("serviceprocessor3-reply")
                .partitions(2)
                .replicas(1)
                .build();
    }
}