package com.logicore.rest.services.simulatorprocessor.flow;

import com.logicore.rest.services.simulatorprocessor.model.payment.PaymentMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FlowAction {

    PaymentMessage paymentMessage;
    String topic;
    public FlowAction(PaymentMessage paymentMessage, String topic) {
        this.paymentMessage = paymentMessage;
        this.topic = topic;
    }

    public Map<String, Object> process() throws IOException {
        String tenantId = paymentMessage.getTenant().getTenantId();

        //Load flow specific to the tennantId
        Selector selector = new Selector(tenantId);
        HashMap<String, Object> flowHashMap = selector.loadFlow();

        //Map flow into an object
        Parser parser = new Parser(flowHashMap);
        Processor processor = new Processor(parser, paymentMessage, topic);
        return processor.processLogic();
    }

}
