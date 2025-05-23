package com.product_service.product_service.entity;

import jakarta.annotation.Generated;
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

@Entity
@Table(name = "product_ratings")
@Getter
@Setter
@NoArgsConstructor
public class ProductRatings {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product productId;
    private Long userId;
    private int rating;
    private String review;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private boolean isDeleted;
    private String status;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;
    @PrePersist
    public void prePersist() {
        this.createdAt = String.valueOf(System.currentTimeMillis());
        this.updatedAt = String.valueOf(System.currentTimeMillis());
        this.isDeleted = false;
        this.status = "active";
    }
    
}
