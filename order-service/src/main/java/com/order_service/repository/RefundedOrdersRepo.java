package com.order_service.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order_service.entity.RefundedOrders;

public interface RefundedOrdersRepo extends JpaRepository<RefundedOrders, Long> {
    // RefundedOrders findByOrderId(Long refundId);
    // RefundedOrders findByOrderIdAndRefundDate(Long refundId, LocalDate refundDate);
    // RefundedOrders findByOrderIdAndRefundStatus(Long refundId, String refundStatus);

}
