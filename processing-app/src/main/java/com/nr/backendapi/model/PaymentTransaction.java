package com.nr.backendapi.model;

import lombok.Data;

@Data
public class PaymentTransaction {
    String id;
    long amount;
    String creditorAccountNumber;
    String debtorAccountNumber;
    String country;
    String timestamp;
}
