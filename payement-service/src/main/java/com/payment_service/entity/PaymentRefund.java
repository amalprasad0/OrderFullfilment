package com.payment_service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.mapping.Join;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "payment_refund")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentRefund {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "payment_link_id")
    private PaymentLinkMap paymentLink;
    @Column(nullable = false)
    private String refundId;
    @Column(nullable = false)
    private String status;
    private LocalDateTime requesDateTime;
    @PrePersist
    public void prePersist() {
        this.requesDateTime = LocalDateTime.now();
    }

}
