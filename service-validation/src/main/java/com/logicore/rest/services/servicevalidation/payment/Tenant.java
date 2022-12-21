package com.logicore.rest.services.servicevalidation.payment;

public class Tenant {

    private String tenantType;
    private String tenantId;
    private String tenantFlow;
    private String tenantDB;
    private String tenantDbSchema;

    public String getTenantType() {
        return tenantType;
    }

    public void setTenantType(String tenantType) {
        this.tenantType = tenantType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantFlow() {
        return tenantFlow;
    }

    public void setTenantFlow(String tenantFlow) {
        this.tenantFlow = tenantFlow;
    }

    public String getTenantDB() {
        return tenantDB;
    }

    public void setTenantDB(String tenantDB) {
        this.tenantDB = tenantDB;
    }

    public String getTenantDbSchema() {
        return tenantDbSchema;
    }

    public void setTenantDbSchema(String tenantDbSchema) {
        this.tenantDbSchema = tenantDbSchema;
    }
}
