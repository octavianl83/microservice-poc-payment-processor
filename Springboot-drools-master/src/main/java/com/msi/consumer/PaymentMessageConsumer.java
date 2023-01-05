package com.msi.consumer;


import com.msi.service.KafkaService;
import com.msi.service.PaymentMessageService;
import lombok.extern.slf4j.Slf4j;
import model.payment.PaymentMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class PaymentMessageConsumer {


    @Autowired
    private PaymentMessageService paymentMessageService;

    @Autowired
    KafkaService kafkaService;

    private String ruleengineTopic = "ruleengine";

    @KafkaListener(topics = {"ruleengine"})
    public void onMessage(ConsumerRecord<Integer, String> customerRecord) throws Exception {

        log.debug("ConsumerRecord : {}", customerRecord);

        PaymentMessage paymentMessage = paymentMessageService.processPaymentMessage(customerRecord);

        if (paymentMessage.getMessageProcessStatus().getRuleName().equals("NOT AVAILABLE")) {
            log.info("Message not mathing any active rule. Discard it." + paymentMessage.toString());
        } else {
            kafkaService.kafkaSend(paymentMessage, paymentMessage.getMessageProcessStatus().getTopic());
        }

    }
}
