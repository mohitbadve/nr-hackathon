package com.nr.backendapi.controller;

import com.nr.backendapi.model.PaymentTransaction;
import com.nr.backendapi.service.PaymentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PaymentTransactionController {
    @Autowired
    PaymentTransactionService paymentTransactionService;

    @GetMapping("/debit-transactions")
    public ResponseEntity<List<PaymentTransaction>> getDebitTransactions(
            @RequestParam("creditorAccountNumber") String creditorAccountNumber){
        return ResponseEntity.ok(paymentTransactionService.chaos(creditorAccountNumber));
    }
}
