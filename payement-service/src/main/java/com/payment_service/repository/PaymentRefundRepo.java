package com.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment_service.entity.PaymentRefund;

public interface PaymentRefundRepo extends JpaRepository<PaymentRefund, Long> {
    

}
