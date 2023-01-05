package com.logicore.rest.services.simulatorprocessor.flow;

import model.payment.ActionStatus;
import model.payment.PaymentMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Processor {

    private Parser parser = null;
    private PaymentMessage paymentMessage = null;
    private String topic = null;
    public Processor(Parser parser, PaymentMessage paymentMessage, String topic) {
        this.parser = parser;
        this.paymentMessage = paymentMessage;
        this.topic = topic;
    }

    private String getEntryPoint() {
        String ruleName = parser.entryPoints.get(topic);
        return ruleName;
    }

    private Map<String, String> getTask() {
        Map<String, String> taskDetails = null;
        String messageStatus = paymentMessage.getMessageProcessStatus().getStatus();
        if (parser.tasks != null) {
            taskDetails = parser.tasks.get(messageStatus);
        }
        return taskDetails;
    }

    private String getExitPoint() {
        String messageStatus = paymentMessage.getMessageProcessStatus().getStatus();
        String kafkaTopic = parser.exitPoints.get(messageStatus);
        return kafkaTopic;
    }

    private void ruleEntryProcessor(String ruleNameEntry) {
        paymentMessage.getMessageProcessStatus().setRuleName(ruleNameEntry);
        paymentMessage.getMessageProcessStatus().setActionStatus(ActionStatus.RULEENGINE);
    }

    private void ruleExitProcessor(String kafkaTopic) {
        paymentMessage.getMessageProcessStatus().setTopic(kafkaTopic);
        paymentMessage.getMessageProcessStatus().setActionStatus(ActionStatus.KAFKA);
    }

    private void taskProcessor(Map<String, String> task) {
        String ruleName = task.get("RuleSet");
//        String commitOnComplete = task.get("CommitOnComplete");
        paymentMessage.getMessageProcessStatus().setRuleName(ruleName);
        paymentMessage.getMessageProcessStatus().setActionStatus(ActionStatus.RULEENGINE);
    }

    public Map<String, Object> processLogic() {
        log.debug("FlowProcessor: Enter into main process method");
        //First we check if there are tasks to be processed
        Map<String, String> task = getTask();
        String ruleNameExit = getExitPoint();
        String ruleNameEntry = getEntryPoint();

        Map<String, Object> actionMap = new HashMap<>();

        if (task != null) {
            //Process the task
            log.debug("FlowProcessor: Enter into task processor {}", task);
            taskProcessor(task);
            actionMap.put("topic", "ruleengine");
        } else if (ruleNameExit != null) {
            //Process the ExitPoint
            log.debug("FlowProcessor: Enter into exitPoint processor {}", ruleNameExit);
            ruleExitProcessor(ruleNameExit);
            actionMap.put("topic", paymentMessage.getMessageProcessStatus().getTopic());
        } else if (ruleNameEntry != null) {
            //Process the EntryPoint
            log.debug("FlowProcessor: Enter into entryPoint processor {}", ruleNameEntry);
            ruleEntryProcessor(ruleNameEntry);
            actionMap.put("topic", "ruleengine");
        }
        //Set externalProcess on true
        paymentMessage.getMessageProcessStatus().setExternalProcesed(true);

        actionMap.put("message", paymentMessage);
        return actionMap;
    }


}


