package com.logicore.rest.services.serviceflowprocessor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicore.rest.services.serviceflowprocessor.model.payment.PaymentMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentMessageService {

    @Autowired
    ObjectMapper objectMapper;

    public PaymentMessage processPaymentMessage(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        PaymentMessage paymentMessage = objectMapper.readValue(consumerRecord.value(), PaymentMessage.class);
        log.info("payment message: {}", paymentMessage);
        save(paymentMessage);
        return paymentMessage;
    }

    private void save(PaymentMessage paymentMessage) {
        log.info("Succesfully persisted the payment message {}", paymentMessage);

    }


}
