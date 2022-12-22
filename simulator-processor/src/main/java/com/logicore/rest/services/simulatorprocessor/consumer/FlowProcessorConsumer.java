package com.logicore.rest.services.simulatorprocessor.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicore.rest.services.simulatorprocessor.service.KafkaService;
import com.logicore.rest.services.simulatorprocessor.flow.FlowAction;
import com.logicore.rest.services.simulatorprocessor.model.payment.PaymentMessage;
import com.logicore.rest.services.simulatorprocessor.service.PaymentMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@Component
@Slf4j
public class FlowProcessorConsumer {


    @Autowired
    private PaymentMessageService paymentMessageService;

    @Autowired
    KafkaService kafkaService;

    private String ruleengineTopic = "ruleengine";

    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(topics = {"volpay.sanctions.send", "volpay.accountlookup.send", "volpay.fundscontrol.send", "volpay.rtp-accountposting.send"})
    public void onMessage(ConsumerRecord<Integer, String> customerRecord) throws IOException {
        log.info("ConsumerRecord : {}", customerRecord);
        PaymentMessage paymentMessage = paymentMessageService.processPaymentMessage(customerRecord);

        FlowAction flowAction = new FlowAction(paymentMessage, customerRecord.topic());
        Map<String, Object> actionMap = flowAction.process();

        kafkaService.kafkaSend((PaymentMessage) actionMap.get("message"), (String) actionMap.get("topic"));

    }

    @KafkaListener(topics = {"volpay.rtp-transmit.send"})
    public void onMessageRest(ConsumerRecord<Integer, String> customerRecord) throws IOException, URISyntaxException {
        log.info("ConsumerRecord : {}", customerRecord);
        PaymentMessage paymentMessage = paymentMessageService.processPaymentMessage(customerRecord);

        URI uri = new URI("http://localhost:" + "7070" + "/paymentSent/");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> result = restTemplate.postForEntity(uri, paymentMessage, PaymentMessage.class);

    }
}
