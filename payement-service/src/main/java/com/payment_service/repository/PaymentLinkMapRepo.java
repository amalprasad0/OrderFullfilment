package com.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment_service.entity.PaymentLinkMap;

public interface PaymentLinkMapRepo extends JpaRepository<PaymentLinkMap, Long> {
    
}
