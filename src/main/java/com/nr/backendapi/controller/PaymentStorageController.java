package com.nr.backendapi.controller;

import com.newrelic.api.agent.Trace;
import com.nr.backendapi.model.PaymentTransaction;
import com.nr.backendapi.service.PaymentStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class PaymentStorageController {
    @Autowired
    PaymentStorageService paymentStorageService;

    @PostMapping("/storeTransaction")
    @Trace(dispatcher = true, nameTransaction = true)
    public ResponseEntity<PaymentTransaction> storeTxn(
            @RequestBody PaymentTransaction paymentTransaction){
        log.info("{}-ReceivedForStoring-storeTxn", paymentTransaction.getId());
        return ResponseEntity.ok(paymentStorageService.storeTxn(paymentTransaction));
    }
}
