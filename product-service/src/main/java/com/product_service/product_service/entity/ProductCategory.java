package com.product_service.product_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_categories")
@Getter
@Setter
@NoArgsConstructor
public class ProductCategory {
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @jakarta.persistence.Column(unique = true, nullable = false, updatable = false)
    private String categoryCode = java.util.UUID.randomUUID().toString();
    @jakarta.persistence.Column(nullable = false)
    private String categoryName;
    @jakarta.persistence.Column(nullable = true)
    private String categoryDescription;
    @jakarta.persistence.Column(nullable = false, updatable = false)
    private long createdBy;
    @jakarta.persistence.Column(nullable = false)
    private long updatedBy;
    @jakarta.persistence.Column(nullable = false, updatable = true)
    private boolean isDeleted;
    @jakarta.persistence.Column(nullable = false, updatable = true)
    private boolean isActive;
}
