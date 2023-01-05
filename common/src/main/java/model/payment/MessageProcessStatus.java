package model.payment;

import lombok.Data;

@Data
public class MessageProcessStatus {

    private String ruleName;
    private String status;
    private String topic;
    private ActionStatus actionStatus;
    private Boolean externalProcesed;
}
