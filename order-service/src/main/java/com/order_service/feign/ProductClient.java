package com.order_service.feign;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.order_service.models.Response;

@FeignClient(name = "PRODUCT-SERVICE", path = "/api/v1/products")
public interface ProductClient {
    @GetMapping("getallproducts")
    ResponseEntity<Map<String, Object>> getAllProducts();
    @PostMapping("getproductbyid")
    ResponseEntity<Map<String, Object>> getProductById(@RequestParam Long id);
}
