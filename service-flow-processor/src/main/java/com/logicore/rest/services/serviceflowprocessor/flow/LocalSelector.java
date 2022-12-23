package com.logicore.rest.services.serviceflowprocessor.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicore.rest.services.serviceflowprocessor.model.payment.PaymentMessage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LocalSelector implements Selector {

    private String tenant = null;

    public LocalSelector(String tenantId) {
        this.tenant = tenantId;
    }

    public String createFlowName(String tenantId) {
        return "process-config".concat("-").concat(tenantId).concat(".json");
    }

    @Override
    public HashMap<String, Object> loadFlow() throws IOException {
        String fileName = createFlowName(this.tenant);
        File file = new File(this.getClass().getClassLoader().getResource(fileName).getFile());
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> flowConfig = mapper.readValue(file, HashMap.class);
        return flowConfig;
    }


    public static void main(String[] args) throws IOException {
        Selector flowSelector = new LocalSelector("tenant1");
        HashMap myFlow = flowSelector.loadFlow();

    }
}
