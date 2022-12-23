package com.research.processconfig.service;

import com.research.processconfig.dto.ProcessConfig;
import com.research.processconfig.repository.ProcessConfigEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ConfigMapper {
    public abstract ProcessConfig toDto(ProcessConfigEntity entity);
    public abstract ProcessConfigEntity toEntity(ProcessConfig config);
}
