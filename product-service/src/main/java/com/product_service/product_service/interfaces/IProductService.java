package com.product_service.product_service.interfaces;

import com.product_service.product_service.models.Response;

public interface IProductService {
    public Response<Long> AddProduct(com.product_service.product_service.models.AddProduct entity);
}
