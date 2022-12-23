package com.research.processconfig.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessConfigRepository extends MongoRepository<ProcessConfigEntity, ObjectId> {
    ProcessConfigEntity findFirstByTenantIdOrderByVersionDesc(String tenantId);
}
