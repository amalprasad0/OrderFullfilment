package com.order_service.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.order_service.models.Response;

@FeignClient(name = "NOTIFICATION-SERVICE", path = "/api/v1/email")
public interface NotificationClient {
    @PostMapping("deleiverEmail")
    public ResponseEntity<Map<String,Object>> deleiverEmail(@RequestBody Map<String,Object> entity);
}
