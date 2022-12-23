package com.logicore.rest.services.serviceflowprocessor.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicore.rest.services.serviceflowprocessor.processconfig.ProcessConfig;
import com.logicore.rest.services.serviceflowprocessor.processconfig.ProcessConfigMgmtProxy;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class DBSelector implements Selector {

    private ProcessConfigMgmtProxy processConfigMgmtProxy;
    private String tenantId;

    public DBSelector(ProcessConfigMgmtProxy processConfigMgmtProxy, String tenantId) {
        this.processConfigMgmtProxy = processConfigMgmtProxy;
        this.tenantId = tenantId;
    }

    @Override
    public HashMap<String, Object> loadFlow() throws IOException {
        ProcessConfig tenantConfig = processConfigMgmtProxy.getTenantConfig(tenantId);
        if (tenantConfig != null) {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> hashMap = mapper.readValue(tenantConfig.getConfig(), HashMap.class);
            return hashMap;
        }
        throw new NoSuchElementException("No DB process config found for tenant");
    }
}
