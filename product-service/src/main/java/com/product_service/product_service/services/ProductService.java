package com.product_service.product_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product_service.product_service.entity.Product;
import com.product_service.product_service.interfaces.IProductCategory;
import com.product_service.product_service.interfaces.IProductService;
import com.product_service.product_service.models.Response;
import com.product_service.product_service.repository.ProductRepository;

import org.springframework.cache.annotation.Cacheable;

@Service
public class ProductService implements IProductService {
    @Autowired
    public IProductCategory productCategoryRepository;
    @Autowired
    public ProductRepository productRepository;
    @Override
    public Response<Long> AddProduct(com.product_service.product_service.models.AddProduct entity) {
        try{
            var productCategoryOpt = productCategoryRepository.findById(entity.getProductCategoryId());
            if (productCategoryOpt.isEmpty()) {
                return Response.error("Failed to add product: Product Category ID does not exist");
            }
            Product product = new Product();
            product.setProductName(entity.getProductName());
            product.setProductDescription(entity.getProductDescription());
            product.setBrand(entity.getBrand());
            product.setProductCategory(productCategoryOpt.get());
            product.setCreatedBy(entity.getCreatedBy());
            product.setUpdatedBy(entity.getUpdatedBy());
            product.setDeleted(entity.isDeleted());
            product.setProductImageUrl(entity.getProductImageUrl());
            product.setActive(entity.isActive());

            var savedProduct=productRepository.save(product);
            var id = savedProduct.getId(); 
            if (id == null) {
                return Response.error("Failed to add product: ID is null");
            }
            return Response.success(id, "Product added successfully");
        }catch(Exception e){
            return Response.error("Failed to add product: " + e.getMessage());
        }
    }
    @Override
    public Response<List<Product>> GetAllProduct(){
        try{
            var products = productRepository.findAll();
            if (products.isEmpty()) {
                return Response.error("Failed to get all products: No products found");
            }
            return Response.success(products, "Products retrieved successfully");
        }catch(Exception e){
            return Response.error("Failed to get all products: " + e.getMessage());
        }
    }
    @Override
    @Cacheable(value = "product", key = "#id")
    public Response<Product> GetProductById(Long id) {
        try{
            var productOpt = productRepository.findById(id);
            if (productOpt.isEmpty()) {
                return Response.error("Failed to get product: Product ID does not exist");
            }
            return Response.success(productOpt.get(), "Product retrieved successfully");
        }catch(Exception e){
            return Response.error("Failed to get product: " + e.getMessage());
        }
    }
    
}
