package com.logicore.rest.services.servicetransformation.flow;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import com.logicore.rest.services.servicetransformation.paymenttransform.Transform;
import model.payment.PaymentMessage;

public class FlowAction {

    PaymentMessage paymentMessage;
    String topic;
    private Selector selector;

    private Transform transform;

    public FlowAction(Selector selector, PaymentMessage paymentMessage, String topic, Transform transform) {
        this.selector = selector;
        this.paymentMessage = paymentMessage;
        this.topic = topic;
        this.transform = transform;
    }

    public Map<String, Object> process() throws IOException, InterruptedException, URISyntaxException {
        String tenantId = paymentMessage.getTenant().getTenantId();

        //Load flow specific to the tennantId
        HashMap<String, Object> flowHashMap = selector.loadFlow(tenantId);

        //Map flow into an object
        Parser parser = new Parser(flowHashMap);
        Processor processor = new Processor(parser, paymentMessage, topic, transform);
        return processor.processLogic();
    }

}
