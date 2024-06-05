package com.nr.backendapi.controller;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.nr.backendapi.model.PaymentTransaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
@Slf4j
public class PerformanceTestingController {

    @Autowired
    RestTemplate restTemplate;

    String url = "http://localhost:8080/uploadPayment";

    @PostMapping("/performanceApp")
    public void performanceTest() {
        /*{
            "amount": 400,
            "creditorAccountNumber": "1000000001",
            "debtorAccountNumber": "1000000002",
            "timestamp": "2023-02-09 19:00:21",
            "country": "SG"
        }*/
        List<String> countries = Arrays.asList("SG", "IND", "AUS", "US", "UK");

        for (int i=0;i<1000;i++) {
            long amount = new Random().nextLong(1, 10000);
            String creditorAccountNumber = RandomStringUtils.randomNumeric(10);
            String debtorAccountNumber = RandomStringUtils.randomNumeric(10);
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            String country = countries.get(new Random().nextInt(0, 5));
            PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                    .amount(amount)
                    .creditorAccountNumber(creditorAccountNumber)
                    .debtorAccountNumber(debtorAccountNumber)
                    .timestamp(timestamp)
                    .country(country).build();
            try{
                var resp = restTemplate.
                        postForEntity(url, paymentTransaction, String.class);
                log.info("Done: {}", i);
            }
            catch(Exception e){
                log.info("Done With Exception: {}", i);
            }

        }

    }
}
