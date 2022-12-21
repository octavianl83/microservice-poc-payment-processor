package com.logicore.rest.services.servicetransformation;

public class ServiceProcessed4 {

    private int id;
    private boolean processed;


    public ServiceProcessed4() {
    }

    public ServiceProcessed4(int id, boolean processed) {
        this.id = id;
        this.processed = processed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
