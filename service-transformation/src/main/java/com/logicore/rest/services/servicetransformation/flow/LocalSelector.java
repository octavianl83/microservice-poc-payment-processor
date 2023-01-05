package com.logicore.rest.services.servicetransformation.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Component("localFlow")
public class LocalSelector implements Selector {

    public static void main(String[] args) throws IOException {
        Selector flowSelector = new LocalSelector();
        HashMap myFlow = flowSelector.loadFlow("tenant1");

    }

    public String createFlowName(String tenantId) {
        return "transform-config".concat("-").concat(tenantId).concat(".json");
    }

    @Override
    public HashMap<String, Object> loadFlow(String tenantId) throws IOException {
        log.info("Loading transform config from file for tenant: " + tenantId);
        String fileName = createFlowName(tenantId);
        File file = new File(this.getClass().getClassLoader().getResource(fileName).getFile());
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> flowConfig = mapper.readValue(file, HashMap.class);
        return flowConfig;
    }

    @Override
    public void clearCacheFlow(String tenantId) {

    }
}
