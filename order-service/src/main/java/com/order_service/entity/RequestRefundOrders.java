package com.order_service.entity;

import java.time.LocalDateTime;


import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "request_refund_orders")
@Getter
@Setter
@NoArgsConstructor
public class RequestRefundOrders {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "order_id")
    @ManyToOne
    private Orders orderId;
    private String refundStatus;
    private String refundReason;
    private LocalDateTime requestDate = LocalDateTime.now();
    private LocalDateTime refundDate;
}
