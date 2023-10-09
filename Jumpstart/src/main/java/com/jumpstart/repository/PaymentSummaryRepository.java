package com.jumpstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jumpstart.entity.PaymentSummary;

public interface PaymentSummaryRepository extends JpaRepository<PaymentSummary, Long> {
    // Define any custom queries or methods if needed
}
