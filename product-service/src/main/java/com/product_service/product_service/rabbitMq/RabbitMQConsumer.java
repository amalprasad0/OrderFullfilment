package com.product_service.product_service.rabbitMq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_service.product_service.interfaces.IStockInventoryService;
import com.product_service.product_service.models.ReserveStock;
@Component
public class RabbitMQConsumer {
    @Autowired private IStockInventoryService inventoryService;
    @RabbitListener(queues = "product-queue")
    public void receiveMessage(String message)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ReserveStock reserveStock = mapper.readValue(message, ReserveStock.class);
            System.out.println("Processed product ID: " + reserveStock.getProductId());
            System.out.println("Received message: " + message);
            inventoryService.releaseStock(reserveStock);
        } catch (Exception e) {
            System.out.println("Error in COnvertion");
        }
       
    }
}
