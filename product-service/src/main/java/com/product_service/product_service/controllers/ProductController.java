package com.product_service.product_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product_service.product_service.interfaces.IProductService;
import com.product_service.product_service.models.AddProduct;
import com.product_service.product_service.models.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("GetAllProducts")
    public String getAllProducts() {
        return new String();
    }

    @PostMapping("AddProduct")
    public ResponseEntity<Response<Long>> postMethodName(@RequestBody AddProduct entity) {

        return ResponseEntity.ok(productService.AddProduct(entity));
    }

}
