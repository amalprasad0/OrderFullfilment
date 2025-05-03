package com.order_service.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.order_service.feign.PaymentClient;
import com.order_service.models.CreatePaymentLink;

@Component
public class PaymentComponents {
    
    @Autowired 
    private PaymentClient paymentClient;
    

    public Map<String, Object> CreateOrderPaymentLink(CreatePaymentLink createPaymentLink) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Map<String, Object> paymentLinkParams = new HashMap<>();
            paymentLinkParams.put("productName", createPaymentLink.getProductName());
            paymentLinkParams.put("unit_amount", createPaymentLink.getUnit_amount());
            paymentLinkParams.put("currency", createPaymentLink.getCurrency());
            paymentLinkParams.put("quantity", createPaymentLink.getQuantity());
            paymentLinkParams.put("orderId", createPaymentLink.getOrderId());

            ResponseEntity<Map<String, Object>> response = paymentClient.generatePaymentLink(paymentLinkParams);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody == null || !Boolean.TRUE.equals(responseBody.get("success"))) {
                responseMap.put("success", false);
                responseMap.put("error", "Couldn't generate link. [Error from Payment Service]");
                return responseMap;
            }

            Map<String, Object> paymentLinkData = (Map<String, Object>) responseBody.get("data");
            String paymentLink = (String) paymentLinkData.get("paymentLink");
            String paymentLinkId = (String) paymentLinkData.get("paymentId"); 

            responseMap.put("success", true);
            responseMap.put("paymentLink", paymentLink);
            responseMap.put("paymentLinkId", paymentLinkId);
            return responseMap;

        } catch (Exception e) {
            responseMap.put("success", false);
            responseMap.put("error", "Error while generating payment link for the order: " + e.getMessage());
            return responseMap;
        }
    }

    public Map<String,Object> refundPayment(Long orderId){
        try {
            Map<String, Object> responseMap = new HashMap<>();
            ResponseEntity<Map<String, Object>> response = paymentClient.refundPayment(orderId);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody == null || !Boolean.TRUE.equals(responseBody.get("success"))) {
                responseMap.put("success", false);
                responseMap.put("error", "Couldn't refund the order. [Error from Payment Service]");
                return responseMap;
            }

            String refundId = (String) responseBody.get("data");
            responseMap.put("success", true);
            responseMap.put("refundId", refundId);
            return responseMap;
        } catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("success", false);
            responseMap.put("error", "Error while refunding the order: " + e.getMessage());
            return responseMap;
        }
    }
}
