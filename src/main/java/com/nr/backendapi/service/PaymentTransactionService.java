package com.nr.backendapi.service;

import com.nr.backendapi.model.PaymentTransaction;
import com.nr.backendapi.repository.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTransactionService {
    @Autowired
    PaymentTransactionRepository paymentTransactionRepository;

    public List<PaymentTransaction> chaos(String creditorAccountNumber){
        return paymentTransactionRepository.findByCreditorAccountNumber(creditorAccountNumber);
    }
}
