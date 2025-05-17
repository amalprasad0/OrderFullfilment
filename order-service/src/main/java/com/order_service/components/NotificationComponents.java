package com.order_service.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.netflix.discovery.converters.Auto;
import com.netflix.spectator.impl.PatternExpr.Not;
import com.order_service.feign.NotificationClient;
import com.order_service.models.Response;

@Component
public class NotificationComponents {
    @Value("${notification.email.orderCreation.header}")
    private String OrderCreationHeader;
    @Value("${notification.email.orderCreation.body}")
    private String OrderCreationBody;
    @Value("${notification.email.orderCreation.footer}")
    private String OrderCreationFooter;
    @Value("${notification.orderCanceled.header}")
    private String OrderCancelHeader;
    @Value("$notification.orderCanceled.body}")
    private String OrderCancelBody;
    @Value("${notification.orderCanceled.footer}")
    private String OrderCancelFooter;
    @Value("${notification.email.updateOrder.header}")
    private String UpdateOrderHeader;
    @Value("${notification.email.updateOrder.body}")
    private String UpdateOrderBody;
    @Value("${notification.email.updateOrder.footer}")
    private String UpdateOrderFooter;
    @Autowired
    private NotificationClient notificationClient;

    public boolean sendEmail(String email, String subject, String body) {
        try {
            HashMap<String, Object> emailData = new HashMap<>();
            emailData.put("email", email);
            emailData.put("subject", subject);
            emailData.put("body", body);
            emailData.put("header", OrderCreationHeader);
            emailData.put("token", "");
            ResponseEntity<Map<String, Object>> response = notificationClient.deleiverEmail(emailData);
            Map<String, Object> responseBody = response.getBody();
            if (responseBody == null || !Boolean.TRUE.equals(responseBody.get("success"))) {
                return false;

            }

            boolean isDelivered = (boolean) responseBody.get("data");
            if (!isDelivered) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error while sending email: " + e.getMessage());
            return false;
        }
    }

    public boolean sendOrderCreationEmail() {
        boolean isEmailSent = sendEmail(OrderCreationHeader, OrderCreationHeader,
                OrderCreationBody + OrderCreationFooter);
        if (!isEmailSent) {
            System.out.println("Failed to send order creation email.");
            return false;
        }
        return true;
    }
    public boolean sendOrderCancelEmail() {
        boolean isEmailSent = sendEmail(OrderCancelHeader, OrderCancelHeader,
                OrderCancelBody + OrderCancelFooter);
        if (!isEmailSent) {
            System.out.println("Failed to send order cancel email.");
            return false;
        }
        return true;
    }
    public boolean sendUpdateOrderEmail() {
        boolean isEmailSent = sendEmail(OrderCreationHeader, OrderCreationHeader,
                OrderCreationBody + OrderCreationFooter);
        if (!isEmailSent) {
            System.out.println("Failed to send order update email.");
            return false;
        }
        return true;
    }

}
