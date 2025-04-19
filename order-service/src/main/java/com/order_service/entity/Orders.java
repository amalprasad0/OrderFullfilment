package com.order_service.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Orders implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderCode = "ORD" + System.currentTimeMillis();
    @Column( nullable = false)
    private long userId;
    @Column( nullable = false)
    private String productId;
    @Column( nullable = false)
    private int quantity;
    @Column( nullable = false)
    private float totalPrice;
    @Column( nullable = false)
    private String orderStatus = "PENDING";
    @Column( nullable = false)
    private String paymentStatus = "PENDING";
    @Column( nullable = false)
    private String paymentMethod;
    @Column( nullable = false)
    private String deliveryAddress;
    @Column( nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();
}
