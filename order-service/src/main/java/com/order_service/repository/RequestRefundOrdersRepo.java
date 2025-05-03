package com.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order_service.entity.RequestRefundOrders;

public interface RequestRefundOrdersRepo extends JpaRepository<RequestRefundOrders, Long> {
    // RequestRefundOrders findByOrderId(Long refundId);
    // RequestRefundOrders findByOrderIdAndRefundStatus(Long orderId, String refundStatus);
    // RequestRefundOrders findByOrderIdAndRefundReason(Long orderId, String refundReason);

}
