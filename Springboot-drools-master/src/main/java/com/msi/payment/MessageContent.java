package com.msi.payment;

import lombok.Data;

@Data
public class MessageContent {

    private String cardNumber;
    private String ownerName;
    private String expireMonth;
    private String expireYear;
    private String securityCode;
    private String currency;
    private String amount;

}
