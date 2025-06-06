package com.product_service.product_service.entity;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock_reservations")
@Getter
@Setter
@NoArgsConstructor
public class StockReservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "stock_reserve_id", nullable = false)
    private Product productId;
    @Column(nullable = false)
    private int stock_reserved;
    @Column(nullable = false)
    private int OrderId;
    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventoryId;
    @Column(nullable = false)
    private long createdBy;
    private long updatedBy;
    @Column(nullable = false)
    private String status;

    private LocalDateTime reservedAt;
    private LocalDateTime releasedAt;

    @PrePersist
    public void prePersist() {
        this.reservedAt = LocalDateTime.now();
        this.releasedAt = null;

    }
    @PreUpdate
    public void preUpdate() {
        this.releasedAt = LocalDateTime.now();
    }
}
