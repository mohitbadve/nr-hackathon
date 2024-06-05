package com.nr.backendapi.repository;

import com.newrelic.api.agent.Trace;
import com.nr.backendapi.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentStorageRepository extends JpaRepository<PaymentTransaction, Long> {
    @Trace(dispatcher = true, nameTransaction = true)
    PaymentTransaction save(PaymentTransaction paymentTransaction);
}
