package com.product_service.product_service.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddInventory {
    private Long id;
    private Long inventoryCode;
    private Long productId;
    private int stockAvailable;
    private int stockReserved;
    @JsonProperty("isActive")
    private boolean isActive;
    private Long createdBy;
    private Long updatedBy;
    
}
