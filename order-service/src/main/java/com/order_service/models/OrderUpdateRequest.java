package com.order_service.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequest {
    private Long orderId;
    private long userId;
    private String productId;
    private int quantity;
    private float totalPrice;
    private String paymentStatus;
    private String paymentMethod;
    private String deliveryAddress;
    // private LocalDateTime orderDateTime = LocalDateTime.now();
}
