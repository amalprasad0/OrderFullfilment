package com.order_service.feign;

import com.order_service.models.ReserveStock;
import com.order_service.models.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @PostMapping("/api/v1/inventory/reserveStock")
    ResponseEntity<Response<Boolean>> reserveStock(@RequestBody ReserveStock entity);

    @PostMapping("/api/v1/inventory/releaseStock")
    ResponseEntity<Response<Boolean>> releaseStock(@RequestBody ReserveStock entity);

    @GetMapping("/api/v1/products/getallproducts")
    ResponseEntity<Map<String, Object>> getAllProducts();

    @PostMapping("/api/v1/products/getproductbyid")
    ResponseEntity<Map<String, Object>> getProductById(@RequestParam Long id);
}
