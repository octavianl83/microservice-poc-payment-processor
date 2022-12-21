package com.logicore.rest.services.simulatorprocessor;

import com.logicore.rest.services.simulatorprocessor.flow.FlowAction;
import com.logicore.rest.services.simulatorprocessor.model.payment.PaymentMessage;
import com.logicore.rest.services.simulatorprocessor.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class ServiceFlowController {

    @Autowired
    private KafkaService kafkaService;

    private String ruleengineTopic = "ruleengine";

    @PostMapping("/flow-processor")
    public void processFlow(@RequestBody PaymentMessage paymentMessage) throws Exception {

        FlowAction flowAction = new FlowAction(paymentMessage, paymentMessage.getMessageProcessStatus().getTopic());
        Map<String, Object> actionMap = flowAction.process();

        kafkaService.kafkaSend((PaymentMessage) actionMap.get("message"), (String) actionMap.get("topic"));

    }

}
