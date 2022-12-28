package com.logicore.rest.services.serviceflowprocessor.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicore.rest.services.serviceflowprocessor.processconfig.ProcessConfig;
import com.logicore.rest.services.serviceflowprocessor.processconfig.ProcessConfigMgmtProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Slf4j
@Component("DBFlow")
@EnableCaching
public class DBSelector implements Selector {

    @Autowired
    protected ProcessConfigMgmtProxy processConfigMgmtProxy;

    @Cacheable(value = "flows")
    @Override
    public HashMap<String, Object> loadFlow(String tenantId) throws IOException {
        ProcessConfig tenantConfig = processConfigMgmtProxy.getTenantConfig(tenantId);
        if (tenantConfig != null) {
            log.info("Loading " + tenantId + " config flow from DB version " + tenantConfig.getVersion());
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> hashMap = mapper.readValue(tenantConfig.getConfig(), HashMap.class);
            return hashMap;
        }
        throw new NoSuchElementException("No DB process config found for tenant");
    }

    @CacheEvict(value = "flows")
    @Override
    public void clearCacheFlow(String tenantId) {
        log.info("Clear cache config for tenant: " + tenantId);
    }
}
