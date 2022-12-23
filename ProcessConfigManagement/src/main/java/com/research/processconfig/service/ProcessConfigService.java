package com.research.processconfig.service;

import com.research.processconfig.dto.ProcessConfig;

public interface ProcessConfigService {
    ProcessConfig save(ProcessConfig config);

    ProcessConfig getTenantConfig(String tenantId);
}
