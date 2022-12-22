package com.logicore.rest.services.servicetransformation.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.logicore.rest.services.servicetransformation.KafkaService;
import com.logicore.rest.services.servicetransformation.payment.PaymentMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentEventsConsumer {

//    @Autowired
//    RuleEngineProxy ruleEngineProxy;

    @Autowired
    KafkaService kafkaService;

    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(topics = "${topics.listen}")
    @SendTo()
    public String onMessage(ConsumerRecord<String, String> customerRecord) throws Exception {
        log.debug("Receive ConsumerRecord : {}", customerRecord);

        PaymentMessage paymentMessage = objectMapper.readValue(customerRecord.value(), PaymentMessage.class);
//        Product product = ruleEngineProxy.getDiscount("gold", 32);
//        kafkaService.kafkaReply(paymentMessage);
        String reply = objectMapper.writeValueAsString(paymentMessage);
        return reply;

    }
}
