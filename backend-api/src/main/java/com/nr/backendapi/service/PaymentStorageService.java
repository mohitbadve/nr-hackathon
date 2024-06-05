package com.nr.backendapi.service;

import com.newrelic.api.agent.Trace;
import com.nr.backendapi.model.PaymentTransaction;
import com.nr.backendapi.repository.PaymentStorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentStorageService {
    @Autowired
    PaymentStorageRepository paymentStorageRepository;

    @Trace(dispatcher = true, nameTransaction = true)
    public PaymentTransaction storeTxn(PaymentTransaction paymentTransaction){
        log.info("{}-SavingToDB-storeTxn", paymentTransaction.getId());
        return paymentStorageRepository.save(paymentTransaction);
    }
}
