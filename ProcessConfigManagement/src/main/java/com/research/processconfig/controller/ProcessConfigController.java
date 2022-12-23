package com.research.processconfig.controller;

import com.research.processconfig.dto.ProcessConfig;
import com.research.processconfig.service.ProcessConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class ProcessConfigController {

    @Autowired
    protected ProcessConfigService processConfigService;

    @PostMapping("/processConfig")
    public ProcessConfig saveTenantConfig(@RequestBody ProcessConfig config) {
        return processConfigService.save(config);
    }

    @GetMapping("/processConfig")
    public ProcessConfig getTenantConfig(@RequestParam String tenantId) {
        return processConfigService.getTenantConfig(tenantId);
    }

    @PostMapping("/uploadConfig")
    public ResponseEntity<String> uploadConfig(@RequestParam String tenantId, @RequestParam("file") MultipartFile file) {
        try {
            String content = new String(file.getBytes());
            ProcessConfig processConfig = ProcessConfig.builder().tenantId(tenantId).config(content).build();
            ProcessConfig savedConfig = processConfigService.save(processConfig);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated tenant config to version: " + savedConfig.getVersion());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/downloadConfig")
    public ResponseEntity<byte[]> downloadConfig(@RequestParam String tenantId) {
        ProcessConfig tenantConfig = processConfigService.getTenantConfig(tenantId);
        if (tenantConfig != null) {
            String tenantConfigFileName = tenantConfig.getTenantId() + "_v" + tenantConfig.getVersion() + ".json";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tenantConfigFileName + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(tenantConfig.getConfig().getBytes(StandardCharsets.UTF_8));
        }

        return ResponseEntity.notFound().build();
    }
}
