package com.research.processconfig.repository;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("processConfigs")
public class ProcessConfigEntity {
    @Id
    private ObjectId id;
    private String tenantId;
    private Integer version;
    private String config;
    private LocalDateTime date;
}
