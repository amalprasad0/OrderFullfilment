package com.product_service.product_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.product_service.product_service.entity.ProductCategory;
import com.product_service.product_service.interfaces.IProductCategory;
import com.product_service.product_service.models.AddCategory;
import com.product_service.product_service.models.Response;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
    @Autowired
    private  IProductCategory productCategoryService;
  
    @PostMapping("AddCategory")
    public  ResponseEntity<Response<Long>> AddCategory(@RequestBody AddCategory entity) {
        Response<Long> response = productCategoryService.AddCategory(entity);
        return ResponseEntity.ok(response);
    }
    @PostMapping("GetAllCategories")
    public ResponseEntity<Response<List<ProductCategory>>> Addcategory(){
        return ResponseEntity.ok(productCategoryService.GetAllCategories());
    }
    
}
