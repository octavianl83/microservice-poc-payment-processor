# microservice-poc-payment-processor


## Generate request with postman
* URL: http://localhost:7200/flow-processor
* Message Body:

{
"tenant": {
    "tenantType": "tenantType1",
    "tenantId": "tenant1",
    "tenantFlow": "tenantFlow1",
    "tenantDB": "tenantDB1",
    "tenantDbSchema": "tenantDbSchema1"
},
"transactionId": 12445543,
"messageType": "CreditCard",
"messageContent": {
"cardNumber": "1234123412341234",
"ownerName": "John Doe",
    "expireMonth": "07",
    "expireYear": "2025",
    "securityCode": "123",
    "currency": "EUR",
    "amount": "205.73"
},  
"messageProcessStatus": {
    "ruleName": "",
    "status": "Initiated",
    "topic":  "volpay.instruction.receive",
    "action": "",
    "externalProcesed": false
}
}
