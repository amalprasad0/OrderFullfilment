package com.product_service.product_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product_service.product_service.entity.Product;
import com.product_service.product_service.interfaces.IProductCategory;
import com.product_service.product_service.interfaces.IProductService;
import com.product_service.product_service.models.Response;
import com.product_service.product_service.repository.ProductRepository;

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
    
}
