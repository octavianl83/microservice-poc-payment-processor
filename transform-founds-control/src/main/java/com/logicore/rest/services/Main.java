package com.logicore.rest.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.payment.PaymentMessage;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String paymentMessageStr = args[0];

        PaymentMessage paymentMessage = objectMapper.readValue(paymentMessageStr, PaymentMessage.class);
        paymentMessage.setFoundsControlTransform("transform-founds-control-jar");

        String paymentMessageToStr = objectMapper.writeValueAsString(paymentMessage);
        System.out.println(paymentMessageToStr.toString());

    }
}