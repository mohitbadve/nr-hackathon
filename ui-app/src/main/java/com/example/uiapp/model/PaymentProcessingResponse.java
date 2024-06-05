package com.example.uiapp.model;

import lombok.Data;

import java.util.List;

@Data
public class PaymentProcessingResponse {
    PaymentTransaction paymentTransaction;
    List<Exception> exceptionList;
}
