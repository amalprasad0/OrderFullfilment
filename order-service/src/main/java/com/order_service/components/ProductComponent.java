package com.order_service.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.order_service.feign.ProductClient;
import com.order_service.models.ReserveStock;
import com.order_service.models.Response;

@Component
public class ProductComponent {
    @Autowired private ProductClient productClient;
    public boolean ReserveStock(ReserveStock reserveStock){
        try {
            ResponseEntity<Response<Boolean>> reserveStockReponse= productClient.reserveStock(reserveStock);
            if(reserveStockReponse.getBody().isSuccess()){
                return reserveStockReponse.getBody().getData();
            }
             return reserveStockReponse.getBody().getData();
        } catch (Exception e) {
             return false;
        }
    }
}
