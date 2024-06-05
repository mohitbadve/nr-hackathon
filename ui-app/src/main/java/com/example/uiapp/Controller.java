package com.example.uiapp;

import com.example.uiapp.model.PaymentProcessingResponse;
import com.example.uiapp.model.PaymentTransaction;
import com.newrelic.api.agent.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@org.springframework.stereotype.Controller
@Slf4j
public class Controller {

    @Autowired
    RestTemplate restTemplate;
    String url = "http://localhost:8081/processPayment";

    @PostMapping("/uploadPayment")
    public String uploadPayment(@RequestBody PaymentTransaction paymentTransaction) {
        String txnId = generateTxnId();
        paymentTransaction.setId(txnId);
        log.info("Start,transactionId:{},creditorAccountNumber:{},debtorAccountNumber:{},amount:{},country:{},",
                paymentTransaction.getId(),
                paymentTransaction.getCreditorAccountNumber(),
                paymentTransaction.getDebtorAccountNumber(),
                paymentTransaction.getAmount(),
                paymentTransaction.getCountry());
        var resp = restTemplate.
                postForEntity(url, paymentTransaction, PaymentProcessingResponse.class);
        if (resp.getStatusCode() == HttpStatusCode.valueOf(500)){
            log.info("{}-PaymentProcessingFailed-{}", resp.getBody().getPaymentTransaction().getId(),
                    resp.getBody().getExceptionList());
        } else if (resp.getStatusCode() == HttpStatusCode.valueOf(200)){
            log.info("{}-PaymentProcessingSucessful-{}", resp.getBody().getPaymentTransaction().getId(),
                    resp.getBody().getPaymentTransaction());
        }
        return "index";
    }

    @Trace(dispatcher = true, nameTransaction = true)
    private String generateTxnId(){
        return UUID.randomUUID().toString();
    }
}
