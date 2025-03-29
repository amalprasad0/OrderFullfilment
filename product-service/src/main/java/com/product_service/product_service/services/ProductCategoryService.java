package com.product_service.product_service.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.product_service.product_service.entity.ProductCategory;
import com.product_service.product_service.interfaces.IProductCategory;
import com.product_service.product_service.models.Response;
import com.product_service.product_service.repository.ProductCategoryRespository;


@Service
public class ProductCategoryService implements IProductCategory {
    private final ProductCategoryRespository productCategoryRespo;
    public ProductCategoryService(ProductCategoryRespository productCategoryRespo) {
        this.productCategoryRespo = productCategoryRespo;
    }
    @Override
    public Response<Long> AddCategory(com.product_service.product_service.models.AddCategory entity) {
        try{
            ProductCategory category = new ProductCategory();
            category.setCategoryName(entity.getCategoryName());
            category.setCategoryDescription(entity.getCategoryDescription());
            category.setCreatedBy(entity.getCreatedBy());
            category.setUpdatedBy(entity.getUpdatedBy());
            category.setDeleted(entity.isDeleted());
            category.setActive(entity.isActive());
            category=productCategoryRespo.save(category);
            var id = category.getId();
            if (id == null) {
                return Response.error("Failed to add category: ID is null");
            }
            return Response.success(id, "Category added successfully");
        } catch (Exception e) {
            return Response.error("Failed to add category: " + e.getMessage());
        }
    }
    @Override
    public Optional<ProductCategory> findById(Long id) {
        return productCategoryRespo.findById(id);
    }
}
