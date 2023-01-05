package model.payment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMessage {

    private Tenant tenant;
    private String messageType;
    private String sanctionTransform;
    private String rtpTransmitTransform;
    private String foundsControlTransform;
    private Long transactionId;
    private MessageContent messageContent;
    private MessageProcessStatus messageProcessStatus;

}
