package com.logicore.rest.services.serviceflowprocessor.flow;

import com.logicore.rest.services.serviceflowprocessor.model.payment.PaymentMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FlowAction {

    PaymentMessage paymentMessage;
    String topic;
    private Selector selector;

    public FlowAction(Selector selector, PaymentMessage paymentMessage, String topic) {
        this.selector = selector;
        this.paymentMessage = paymentMessage;
        this.topic = topic;
    }

    public Map<String, Object> process() throws IOException {
        String tenantId = paymentMessage.getTenant().getTenantId();

        //Load flow specific to the tennantId
        HashMap<String, Object> flowHashMap = selector.loadFlow(tenantId);

        //Map flow into an object
        Parser parser = new Parser(flowHashMap);
        Processor processor = new Processor(parser, paymentMessage, topic);
        return processor.processLogic();
    }

}
