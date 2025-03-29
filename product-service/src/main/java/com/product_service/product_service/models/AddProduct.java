package com.product_service.product_service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class AddProduct {
    private String productName;
    private String productDescription;
    
    private String brand;
    private Long productCategoryId;
    private long createdBy;
    private long updatedBy;
    private boolean isDeleted;
    private String productImageUrl;
    private boolean isActive;
}
