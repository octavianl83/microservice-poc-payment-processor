package com.logicore.rest.services.servicetransformation.consumer;


import com.logicore.rest.services.servicetransformation.flow.FlowAction;
import com.logicore.rest.services.servicetransformation.flow.Selector;
import com.logicore.rest.services.servicetransformation.service.KafkaService;
import com.logicore.rest.services.servicetransformation.service.PaymentMessageService;
import lombok.extern.slf4j.Slf4j;
import model.payment.PaymentMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class TransformationProcessorConsumer {

    @Autowired
    KafkaService kafkaService;
    @Autowired
    private PaymentMessageService paymentMessageService;
    @Autowired
    @Qualifier("localFlow")
//    @Qualifier("DBFlow")
    private Selector selector;

    @KafkaListener(topics = {"volpay.sanctions.transform", "volpay.rtptransmit.transform", "volpay.fundscontrol.transform"})
    public void onMessage(ConsumerRecord<Integer, String> customerRecord) throws IOException, InterruptedException {
        log.debug("ConsumerRecord : {}", customerRecord);
        PaymentMessage paymentMessage = paymentMessageService.transformPaymentMessage(customerRecord);

        FlowAction flowAction = new FlowAction(selector, paymentMessage, customerRecord.topic());
        Map<String, Object> actionMap = flowAction.process();

        kafkaService.kafkaSend((PaymentMessage) actionMap.get("message"), (String) actionMap.get("topic"));

    }

    @KafkaListener(topics = "updateFlowConfig")
    public void updateFlowConfig(String tenantId) {
        selector.clearCacheFlow(tenantId);

    }
}
