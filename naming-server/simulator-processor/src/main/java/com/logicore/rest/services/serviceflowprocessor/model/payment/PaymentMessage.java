package com.logicore.rest.services.serviceflowprocessor.model.payment;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentMessage {

    private Tenant tenant;
    private String messageType;

    private Long transactionId;
    private MessageContent messageContent;

    private MessageProcessStatus messageProcessStatus;

}
