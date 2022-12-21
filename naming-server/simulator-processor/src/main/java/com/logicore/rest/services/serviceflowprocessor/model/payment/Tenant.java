package com.logicore.rest.services.serviceflowprocessor.model.payment;

import lombok.Data;

@Data
public class Tenant {

    private String tenantType;
    private String tenantId;
    private String tenantFlow;
    private String tenantDB;
    private String tenantDbSchema;

}
