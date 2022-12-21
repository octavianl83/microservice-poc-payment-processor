package com.logicore.rest.services.serviceflowprocessor.consumer;

import com.logicore.rest.services.serviceflowprocessor.service.KafkaService;
import com.logicore.rest.services.serviceflowprocessor.flow.FlowAction;
import com.logicore.rest.services.serviceflowprocessor.model.payment.PaymentMessage;
import com.logicore.rest.services.serviceflowprocessor.service.PaymentMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class FlowProcessorConsumer {


    @Autowired
    private PaymentMessageService paymentMessageService;

    @Autowired
    KafkaService kafkaService;

    private String ruleengineTopic = "ruleengine";

    @KafkaListener(topics = {"volpay.instruction.receive", "volpay.sanctions.receive", "volpay.accountlookup.receive", "volpay.fundscontrol.receive", "volpay.rtp-accountposting.receive", "volpay.rtp-transmit-ack.receive"})
    public void onMessage(ConsumerRecord<Integer, String> customerRecord) throws IOException {
        log.info("ConsumerRecord : {}", customerRecord);
        PaymentMessage paymentMessage = paymentMessageService.processPaymentMessage(customerRecord);

        FlowAction flowAction = new FlowAction(paymentMessage, customerRecord.topic());
        Map<String, Object> actionMap = flowAction.process();

        kafkaService.kafkaSend((PaymentMessage) actionMap.get("message"), (String) actionMap.get("topic"));

    }
}
