package com.nr.backendapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaymentProcessingResponse {
    PaymentTransaction paymentTransaction;
    List<Exception> exceptionList;
}
