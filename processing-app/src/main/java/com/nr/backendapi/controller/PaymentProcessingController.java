package com.nr.backendapi.controller;

import com.newrelic.api.agent.Trace;
import com.nr.backendapi.model.PaymentProcessingResponse;
import com.nr.backendapi.model.PaymentTransaction;
import com.nr.backendapi.service.PaymentProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class PaymentProcessingController {
    @Autowired
    PaymentProcessingService paymentProcessingService;

    @PostMapping("/processPayment")
    @Trace(dispatcher = true, nameTransaction = true)
    public ResponseEntity<PaymentProcessingResponse> processTransaction(
            @RequestBody PaymentTransaction paymentTransaction){
        log.info("{}-Received-newTxn", "NewTxn");
        return paymentProcessingService.processTxn(paymentTransaction);
    }
}
