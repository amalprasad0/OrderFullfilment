package com.payment_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePaymentLink {
    public String productName;
    public int unit_amount;
    public String currency ;
    public int quantity;
    public long orderId;
}
