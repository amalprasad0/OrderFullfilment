package com.product_service.product_service.entity;


import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor  
// @Document(indexName = "product")

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String productDescription;
    @Column(nullable = false)
    private String Brand;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory productCategory;
    @Column(nullable = false)
    private long createdBy;
    @Column(nullable = false)
    private long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    private boolean isDeleted;
    private String productImageUrl;
    @Column(nullable = false)
    private boolean isActive;
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PostPersist
    public void postPersist() {
        this.updatedAt = LocalDateTime.now();
    }
}
