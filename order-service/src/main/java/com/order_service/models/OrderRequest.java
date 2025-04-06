package com.order_service.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
    private long userId;
    private String productId;
    private int quantity;
    private float totalPrice;
    private String paymentStatus;
    private String paymentMethod;
    private String deliveryAddress;
    private LocalDateTime orderDateTime = LocalDateTime.now();
}
