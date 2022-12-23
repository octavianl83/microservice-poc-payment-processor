package com.logicore.rest.services.serviceflowprocessor;

import com.logicore.rest.services.serviceflowprocessor.flow.DBSelector;
import com.logicore.rest.services.serviceflowprocessor.flow.FlowAction;
import com.logicore.rest.services.serviceflowprocessor.flow.Selector;
import com.logicore.rest.services.serviceflowprocessor.model.payment.PaymentMessage;
import com.logicore.rest.services.serviceflowprocessor.processconfig.ProcessConfigMgmtProxy;
import com.logicore.rest.services.serviceflowprocessor.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ProcessConfigMgmtProxy processConfigService;

    @PostMapping("/flow-processor")
    public void processFlow(@RequestBody PaymentMessage paymentMessage) throws Exception {

        Selector selector = new DBSelector(processConfigService, paymentMessage.getTenant().getTenantId());
        FlowAction flowAction = new FlowAction(selector, paymentMessage, paymentMessage.getMessageProcessStatus().getTopic());
        Map<String, Object> actionMap = flowAction.process();

        kafkaService.kafkaSend((PaymentMessage) actionMap.get("message"), (String) actionMap.get("topic"));

    }

}
