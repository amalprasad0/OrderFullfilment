package com.product_service.product_service.interfaces;

import com.product_service.product_service.models.AddInventory;
import com.product_service.product_service.models.ReserveStock;
import com.product_service.product_service.models.Response;

public interface IStockInventoryService {

    public Response<Long> addInventoryStock(AddInventory entity);
    public Response<Boolean> reserveStock(ReserveStock entity);
    public Response<Boolean> releaseStock(ReserveStock entity);
    public Response<Boolean> sendtoInventoryQueue(ReserveStock entity);
}
