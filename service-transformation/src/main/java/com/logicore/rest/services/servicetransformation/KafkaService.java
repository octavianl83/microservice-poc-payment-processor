package com.logicore.rest.services.servicetransformation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topics.reply}")
    private String REPLY_TOPIC;
    @Autowired
    ObjectMapper objectMapper;

}