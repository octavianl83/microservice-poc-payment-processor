package com.research.processconfig.service;

import com.research.processconfig.dto.ProcessConfig;
import com.research.processconfig.repository.ProcessConfigEntity;
import com.research.processconfig.repository.ProcessConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProcessConfigServiceImpl implements ProcessConfigService {

    @Autowired
    protected ProcessConfigRepository processConfigRepository;

    @Autowired
    protected ConfigMapper configMapper;

    @Override
    public ProcessConfig save(ProcessConfig config) {
        ProcessConfigEntity entity = configMapper.toEntity(config);
        entity.setDate(LocalDateTime.now());
        ProcessConfigEntity existingConfig = processConfigRepository.findFirstByTenantIdOrderByVersionDesc(config.getTenantId());
        if (existingConfig != null) {
            entity.setVersion(existingConfig.getVersion() + 1);
        } else {
            entity.setVersion(1);
        }
        ProcessConfigEntity savedEntity = processConfigRepository.save(entity);
        return configMapper.toDto(savedEntity);
    }

    @Override
    public ProcessConfig getTenantConfig(String tenantId) {
        ProcessConfigEntity latestConfig = processConfigRepository.findFirstByTenantIdOrderByVersionDesc(tenantId);
        ProcessConfig config = configMapper.toDto(latestConfig);
        return config;
    }
}
