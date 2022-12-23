package com.research.processconfig.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ProcessConfig {
    @NotNull
    private String tenantId;
    private Integer version;
    private String config;
    private LocalDateTime date;

}
