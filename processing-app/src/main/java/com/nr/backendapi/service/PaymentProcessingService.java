package com.nr.backendapi.service;

import com.newrelic.api.agent.Trace;
import com.nr.backendapi.model.PaymentProcessingResponse;
import com.nr.backendapi.model.PaymentTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PaymentProcessingService {
    @Autowired
    RestTemplate restTemplate;

    String url = "http://localhost:8082/storeTransaction";

    @Trace(dispatcher = true, nameTransaction = true)
    public ResponseEntity<PaymentProcessingResponse> processTxn(PaymentTransaction paymentTransaction){
        log.info("InProcessing,transactionId:{},creditorAccountNumber:{},debtorAccountNumber:{},amount:{},country:{},",
                paymentTransaction.getId(),
                paymentTransaction.getCreditorAccountNumber(),
                paymentTransaction.getDebtorAccountNumber(),
                paymentTransaction.getAmount(),
                paymentTransaction.getCountry());
        log.info("{}-InProcessing-processTxn", paymentTransaction.getId());
        List<Exception> exceptionList = validateTxn(paymentTransaction);
        if(exceptionList.size() > 0){
            return ResponseEntity.internalServerError().
                    body(PaymentProcessingResponse.builder().exceptionList(exceptionList).build());
        }
        else{
            return ResponseEntity.ok(
                    PaymentProcessingResponse.builder().
                        paymentTransaction(storeUpdatedTxn(paymentTransaction).getBody())
                            .build());
        }
    }

    @Trace(dispatcher = true, nameTransaction = true)
    private List<Exception> validateTxn(PaymentTransaction paymentTransaction){
        List<Exception> exceptionList = new ArrayList<>();
        if(paymentTransaction.getCreditorAccountNumber().length() != 10
        && paymentTransaction.getDebtorAccountNumber().length() != 10
                && paymentTransaction.getAmount() < 0
        ){
            log.error("{}-Exception:{}-validateTxn", paymentTransaction.getId(), "ValidationException");
            exceptionList.add(new RuntimeException("Validation Exception"));
        }
        return exceptionList;
    }

    @Trace(dispatcher = true, nameTransaction = true)
    private ResponseEntity<PaymentTransaction> storeUpdatedTxn(PaymentTransaction paymentTransaction){
        log.info("{}-Storing-storeUpdatedTxn", paymentTransaction.getId());
        return restTemplate.postForEntity(url, paymentTransaction, PaymentTransaction.class);
    }
}
