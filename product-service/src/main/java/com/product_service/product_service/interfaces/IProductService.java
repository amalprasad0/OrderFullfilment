package com.product_service.product_service.interfaces;

import java.util.List;


import com.product_service.product_service.entity.Product;
import com.product_service.product_service.models.Response;
import com.product_service.product_service.models.AddProductRating;
public interface IProductService {
    public Response<Long> AddProduct(com.product_service.product_service.models.AddProduct entity);
    public Response<List<Product>> GetAllProduct();
    public Response<Product> GetProductById(Long id);
    public Response<Boolean> AddProductRating(AddProductRating entity);
}
