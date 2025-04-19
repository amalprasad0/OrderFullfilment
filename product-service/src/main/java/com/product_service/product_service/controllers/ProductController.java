package com.product_service.product_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product_service.product_service.entity.Product;
import com.product_service.product_service.interfaces.IProductService;
import com.product_service.product_service.models.AddProduct;
import com.product_service.product_service.models.Response;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @GetMapping("getallproducts")
    public ResponseEntity<Response<List<Product>>> getAllProducts() {
        return ResponseEntity.ok(productService.GetAllProduct());
    }

    @PostMapping("addproduct")
    public ResponseEntity<Response<Long>> postMethodName(@RequestBody AddProduct entity) {

        return ResponseEntity.ok(productService.AddProduct(entity));
    }

    @PostMapping("getproductbyid")
    // @Cacheable(value = "product", key = "#id")
    public ResponseEntity<Response<Product>> getProductById(@RequestParam Long id) {
        // Cache the Response object instead of ResponseEntity
        Response<Product> productResponse = productService.GetProductById(id);
        return ResponseEntity.ok(productResponse);
    }

}
