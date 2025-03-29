package com.product_service.product_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.product_service.product_service.interfaces.IProductCategory;
import com.product_service.product_service.models.AddCategory;
import com.product_service.product_service.models.Response;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
    @Autowired
    private  IProductCategory productCategoryService;
    // public CategoryController(IProductCategory productCategoryService) {
    //     this.productCategoryService = productCategoryService;
    // }
    @PostMapping("AddCategory")
    public  ResponseEntity<Response<Long>> AddCategory(@RequestBody AddCategory entity) {
        Response<Long> response = productCategoryService.AddCategory(entity);
        return ResponseEntity.ok(response);
    }
    @PostMapping("GetAllCategories")
    public String Addcategory(){
        return new String();
    }
    
}
