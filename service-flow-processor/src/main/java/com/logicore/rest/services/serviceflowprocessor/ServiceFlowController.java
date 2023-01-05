package com.logicore.rest.services.serviceflowprocessor;

import com.logicore.rest.services.serviceflowprocessor.flow.FlowAction;
import com.logicore.rest.services.serviceflowprocessor.flow.Selector;
import com.logicore.rest.services.serviceflowprocessor.service.KafkaService;
import model.payment.PaymentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class ServiceFlowController {

    @Autowired
    private KafkaService kafkaService;

    private String ruleengineTopic = "ruleengine";

    @Autowired
    @Qualifier("localFlow")
//    @Qualifier("DBFlow")
    private Selector selector;

    @PostMapping("/flow-processor")
    public void processFlow(@RequestBody PaymentMessage paymentMessage) throws Exception {
        FlowAction flowAction = new FlowAction(selector, paymentMessage, paymentMessage.getMessageProcessStatus().getTopic());
        Map<String, Object> actionMap = flowAction.process();

        kafkaService.kafkaSend((PaymentMessage) actionMap.get("message"), (String) actionMap.get("topic"));

    }

}
