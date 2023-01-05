package com.logicore.rest.services.servicetransformation.processconfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "process-config-management")
public interface ProcessConfigMgmtProxy {
    @GetMapping("/processConfig")
    public ProcessConfig getTenantConfig(@RequestParam String tenantId);
}
