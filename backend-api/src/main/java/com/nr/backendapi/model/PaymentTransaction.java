package com.nr.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction {
    @Id
    private String id;

    long amount;
    String creditorAccountNumber;
    String debtorAccountNumber;
    String timestamp;
    String country;
}
