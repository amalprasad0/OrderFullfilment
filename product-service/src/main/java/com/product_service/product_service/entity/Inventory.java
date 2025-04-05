package com.product_service.product_service.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long inventoryCode;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product productId;
    @Column(nullable = false)
    private int stock_available;
    @Column(nullable = false)
    private int stock_reserved;
    @Column(nullable = false)
    private long createdBy;
    @Column(nullable = false)
    private long updatedBy;
    @Column(nullable = false)
    private boolean isDeleted;
    @Column(nullable = false)
    private boolean isActive;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PreUpdate 
    public void postPersist() {
       
        this.updatedAt = LocalDateTime.now();
    }
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
