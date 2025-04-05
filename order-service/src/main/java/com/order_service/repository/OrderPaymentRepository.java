package com.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order_service.entity.OrderPaymentMap;

public interface OrderPaymentRepository extends JpaRepository<OrderPaymentMap, Long> {
   

}
