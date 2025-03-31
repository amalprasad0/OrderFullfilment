package com.product_service.product_service.interfaces;

import java.util.List;

import com.product_service.product_service.entity.Product;
import com.product_service.product_service.models.Response;

public interface IProductService {
    public Response<Long> AddProduct(com.product_service.product_service.models.AddProduct entity);
    public Response<List<Product>> GetAllProduct();
    public Response<Product> GetProductById(Long id);
}
