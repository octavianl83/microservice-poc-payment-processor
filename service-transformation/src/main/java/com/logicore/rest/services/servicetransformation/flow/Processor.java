package com.logicore.rest.services.servicetransformation.flow;

import com.logicore.rest.services.servicetransformation.paymenttransform.Transform;
import model.payment.ActionStatus;
import model.payment.PaymentMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class Processor {

    Transform transformationEngine;
    private Parser parser;
    private PaymentMessage paymentMessage;
    private String topic;
    public Processor(Parser parser, PaymentMessage paymentMessage, String topic, Transform transform) {
        this.parser = parser;
        this.paymentMessage = paymentMessage;
        this.topic = topic;
        this.transformationEngine = transform;
    }

    private LinkedHashMap<String, String> getEntryPoint() {
        LinkedHashMap<String, String> ruleName = parser.entryPoints.get(topic);
        return ruleName;
    }

    private void ruleEntryProcessor(LinkedHashMap<String, String> ruleNameEntry) throws IOException, InterruptedException, URISyntaxException {
        String jarTransform = ruleNameEntry.get("Transform");
        String endPoint = ruleNameEntry.get("EndPoint");

        Transform transformEngine = new Transform();
        paymentMessage = transformEngine.transformPaymentMessage(jarTransform, paymentMessage);
        paymentMessage.getMessageProcessStatus().setActionStatus(ActionStatus.KAFKA);
        paymentMessage.getMessageProcessStatus().setStatus(ruleNameEntry.get("Status"));
        paymentMessage.getMessageProcessStatus().setTopic(ruleNameEntry.get("EndPoint"));
    }

    public Map<String, Object> processLogic() throws IOException, InterruptedException, URISyntaxException {
        log.debug("FlowProcessor: Enter into main process method");
        LinkedHashMap<String, String> ruleNameEntry = getEntryPoint();

        Map<String, Object> actionMap = new HashMap<>();

        Boolean externalProcesed = paymentMessage.getMessageProcessStatus().getExternalProcesed();

        if (ruleNameEntry != null) {
            //Process the EntryPoint
            log.debug("FlowProcessor: Enter into entryPoint processor {}", ruleNameEntry);
            ruleEntryProcessor(ruleNameEntry);

            String endPoint = ruleNameEntry.get("EndPoint");
            actionMap.put("topic", endPoint);
        }
        //Set externalProcess on false
        paymentMessage.getMessageProcessStatus().setExternalProcesed(false);

        actionMap.put("message", paymentMessage);
        return actionMap;
    }


}


