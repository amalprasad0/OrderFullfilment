package com.product_service.product_service.interfaces;

import java.util.List;
import java.util.Optional;

import com.product_service.product_service.entity.ProductCategory;
import com.product_service.product_service.models.Response;

public interface IProductCategory {
    public Response<Long> AddCategory(com.product_service.product_service.models.AddCategory entity);
    Optional<ProductCategory> findById(Long id);
    public Response<List<ProductCategory>> GetAllCategories();

}
