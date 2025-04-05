package com.product_service.product_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveStock {
    public Long productId;
    public int StockReserved;
    public int orderId;
    public Long reservedBy;

}
