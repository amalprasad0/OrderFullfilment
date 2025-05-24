package com.product_service.product_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product_service.product_service.entity.Product;
import com.product_service.product_service.interfaces.IProductService;
import com.product_service.product_service.models.AddProduct;
import com.product_service.product_service.models.AddProductRating;
import com.product_service.product_service.models.Response;
import com.product_service.product_service.search.ProductDocument;
import com.product_service.product_service.search.ProductSearchService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ProductSearchService productSearchService;

    @GetMapping("getallproducts")
    public ResponseEntity<Response<List<Product>>> getAllProducts(
            @RequestHeader(value = "X-User-Email", required = false) String userEmail,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if ("USER".equalsIgnoreCase(userRole) && userEmail != null && !userEmail.isEmpty()) {
            return ResponseEntity.ok(productService.GetAllProduct());
        } else {
            Response<List<Product>> response = new Response<>();
            response.setSuccess(false);
            response.setResponseMessage("Access Restricted");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        // return ResponseEntity.ok(productService.GetAllProduct());
    }

    @PostMapping("addproduct")
    public ResponseEntity<Response<Long>> postMethodName(@RequestBody AddProduct entity,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if ("USER".equalsIgnoreCase(userRole) && userEmail != null && !userEmail.isEmpty()) {
            return ResponseEntity.ok(productService.AddProduct(entity));
        } else {
            Response<Long> response = new Response<>();
            response.setSuccess(false);
            response.setResponseMessage("Access Restricted");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @PostMapping("getproductbyid")
    // @Cacheable(value = "product", key = "#id")
    public ResponseEntity<Response<Product>> getProductById(@RequestParam Long id) {
        // Cache the Response object instead of ResponseEntity
        Response<Product> productResponse = productService.GetProductById(id);
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping("addproductrating")
    public ResponseEntity<Response<Boolean>> addProductRating(
            @RequestBody AddProductRating entity,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {

        if ("USER".equalsIgnoreCase(userRole) && userEmail != null && !userEmail.isEmpty()) {
            return ResponseEntity.ok(productService.AddProductRating(entity));
        } else {
            Response<Boolean> response = new Response<>();
            response.setSuccess(false);
            response.setResponseMessage("Access Restricted");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDocument>> searchProducts(@RequestParam("q") String query) {
        Iterable<ProductDocument> results = productSearchService.searchByName(query);
        List<ProductDocument> productList = new ArrayList<>();
        results.forEach(productList::add);
        return ResponseEntity.ok(productList);
    }
}
