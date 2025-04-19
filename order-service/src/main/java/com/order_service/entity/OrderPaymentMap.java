package com.order_service.entity;

import java.io.Serializable;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
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
@Table(name = "order_payment_map")
@Getter
@Setter
@NoArgsConstructor
public class OrderPaymentMap implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orderId;
    @Column(nullable = false)
    private int paymentId;
    @Column(nullable = false)
    private String paymentStatus;
    @Column(nullable = false)
    private String paymentMethod;
    @Column(nullable = false)
    private String paymentDate;
    @Column(nullable = false)
    private String paymentResponse;
}
