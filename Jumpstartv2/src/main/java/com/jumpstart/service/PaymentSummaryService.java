package com.jumpstart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumpstart.entity.PaymentSummary;
import com.jumpstart.repository.PaymentSummaryRepository;

@Service
public class PaymentSummaryService {

    @Autowired
    private PaymentSummaryRepository paymentSummaryRepository; // Create a repository to interact with the database

    public PaymentSummary savePaymentSummary(PaymentSummary paymentSummary) {
        return paymentSummaryRepository.save(paymentSummary);
    }
}
