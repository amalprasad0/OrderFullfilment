package com.product_service.product_service.rabbitMq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class RabbitMQProducer {
 @Autowired private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message)
    {
        rabbitTemplate.convertAndSend(
            "product-exchange", "stock-release", message);
    }
}
