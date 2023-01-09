package com.logicore.rest.services.servicetransformation.paymenttransform;

import model.payment.PaymentMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component("TransformFactory")
public class TransformFactoryImp implements Transform{
    @Override
    public PaymentMessage transformPaymentMessage(String jarName, PaymentMessage paymentMessage) throws IOException, InterruptedException, URISyntaxException {
        return null;
    }
}
