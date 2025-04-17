package com.order_service.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetails {
    private Long orderId;
    private String productName;
    private Long productId;
    private String deliveryAddress;
    private LocalDateTime orderDate;
    private int quantity;
    private Long userId;
    private String paymentStatus;
    private String orderStatus;
    private String productImgUrl; 
}
