package com.nr.backendapi.controller;

import com.newrelic.api.agent.Trace;
import com.nr.backendapi.model.PaymentProcessingResponse;
import com.nr.backendapi.model.PaymentTransaction;
import com.nr.backendapi.service.PaymentStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class PaymentStorageController {
    @Autowired
    PaymentStorageService paymentStorageService;

    @PostMapping("/storeTransaction")
    @Trace(dispatcher = true, nameTransaction = true)
    public ResponseEntity<PaymentProcessingResponse> storeTxn(
            @RequestBody PaymentTransaction paymentTransaction){
        try {
            PaymentTransaction updated = paymentStorageService.storeTxn(paymentTransaction);
            log.info("End,transactionId:{},creditorAccountNumber:{},debtorAccountNumber:{},amount:{},country:{},",
                    paymentTransaction.getId(),
                    paymentTransaction.getCreditorAccountNumber(),
                    paymentTransaction.getDebtorAccountNumber(),
                    paymentTransaction.getAmount(),
                    paymentTransaction.getCountry());
            return ResponseEntity.ok(
                    PaymentProcessingResponse.builder().
                            paymentTransaction(updated)
                            .build());

        } catch (Exception exception){
            List<Exception> exceptionList = new ArrayList<>();
            exceptionList.add(new RuntimeException("Chaos Monkey Exception"));
            log.info("Exception,transactionId:{},creditorAccountNumber:{},debtorAccountNumber:{},amount:{},country:{},",
                    paymentTransaction.getId(),
                    paymentTransaction.getCreditorAccountNumber(),
                    paymentTransaction.getDebtorAccountNumber(),
                    paymentTransaction.getAmount(),
                    paymentTransaction.getCountry());
            return ResponseEntity.internalServerError().
                    body(PaymentProcessingResponse.builder().exceptionList(exceptionList).build());
        }
    }
}
