package com.logicore.rest.services.serviceflowprocessor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicore.rest.services.serviceflowprocessor.model.payment.PaymentMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class KafkaService {

    @Value("${topics.send}")
    private String SEND_TOPICS;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;


    public void kafkaSend(PaymentMessage request, String topic) throws JsonProcessingException {

        Integer key = 1234;
        String value = objectMapper.writeValueAsString(request);

        ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, topic);

        ListenableFuture<SendResult<Integer, String>> sendResultListenableFuture = kafkaTemplate.send(producerRecord);
        sendResultListenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                handleFailure(throwable);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result, topic);
            }
        });
    }

    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {
        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));
        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    }

    public void handleSuccess(Integer key, String value, SendResult<Integer, String> result, String topic) {
        log.debug("Message sent successfully with key {} and value {} and topic {} partition is {}", key, value, topic, result.getRecordMetadata().partition());
    }

    public void handleFailure(Throwable ex) {
        log.error("Error sending the message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        }catch (Throwable throwable) {
            log.error("Error in OnFailure {}", throwable.getMessage());
        }
    }
}