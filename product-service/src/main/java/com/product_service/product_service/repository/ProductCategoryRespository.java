package com.product_service.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product_service.product_service.entity.ProductCategory;

public interface ProductCategoryRespository  extends JpaRepository<ProductCategory, Long> {
    // Custom query methods can be defined here if needed
    // For example, to find a category by its name:
    // Optional<ProductCategory> findByCategoryName(String categoryName);

}
