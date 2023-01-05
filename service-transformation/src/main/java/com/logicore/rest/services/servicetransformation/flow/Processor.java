package com.logicore.rest.services.servicetransformation.flow;

import com.logicore.rest.services.servicetransformation.paymenttransform.Transform;
import model.payment.ActionStatus;
import model.payment.PaymentMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class Processor {

    private Parser parser;
    private PaymentMessage paymentMessage;
    private String topic;
    public Processor(Parser parser, PaymentMessage paymentMessage, String topic) {
        this.parser = parser;
        this.paymentMessage = paymentMessage;
        this.topic = topic;
    }

    private LinkedHashMap<String, String> getEntryPoint() {
        LinkedHashMap<String, String> ruleName = parser.entryPoints.get(topic);
        return ruleName;
    }

    private void ruleEntryProcessor(LinkedHashMap<String, String> ruleNameEntry) throws IOException, InterruptedException {
        String jarTransform = ruleNameEntry.get("Transform");
        String endPoint = ruleNameEntry.get("EndPoint");

        paymentMessage = Transform.transformPaymentMessage(jarTransform, paymentMessage);

        paymentMessage.getMessageProcessStatus().setTopic(endPoint);
        paymentMessage.getMessageProcessStatus().setActionStatus(ActionStatus.KAFKA);
    }

    public Map<String, Object> processLogic() throws IOException, InterruptedException {
        log.debug("FlowProcessor: Enter into main process method");
        LinkedHashMap<String, String> ruleNameEntry = getEntryPoint();

        Map<String, Object> actionMap = new HashMap<>();

        Boolean externalProcesed = paymentMessage.getMessageProcessStatus().getExternalProcesed();

        if (ruleNameEntry != null) {
            //Process the EntryPoint
            log.debug("FlowProcessor: Enter into entryPoint processor {}", ruleNameEntry);
            ruleEntryProcessor(ruleNameEntry);
            actionMap.put("topic", "ruleengine");
        }
        //Set externalProcess on false
        paymentMessage.getMessageProcessStatus().setExternalProcesed(true);


        actionMap.put("message", paymentMessage);
        return actionMap;
    }


}


