package com.order_service.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE", path = "/api/v1/Payment")

public interface PaymentClient {
    @PostMapping("/create-payment-link")
    public ResponseEntity<Map<String, Object>> generatePaymentLink(@RequestBody Map<String, Object> entity);

    @PostMapping("/check-payment-status")
    public ResponseEntity<Map<String, Object>> getPaymentStatus(@RequestBody Map<String, Object> requestBody);
}
