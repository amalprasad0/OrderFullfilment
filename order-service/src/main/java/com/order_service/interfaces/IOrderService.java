package com.order_service.interfaces;

import com.order_service.models.OrderCancelRequest;
import com.order_service.models.OrderRequest;
import com.order_service.models.OrderUpdateRequest;
import com.order_service.models.Response;

public interface IOrderService {
    public Response<Long> createOrder(OrderRequest orderRequest) ;
    public Response<Boolean> canceledOrder(OrderCancelRequest orderCancelRequest) ;
    public Response<Boolean> updateOrder(OrderUpdateRequest orderUpdateRequest) ;
    public Response<Boolean> getOrderById(Long orderId) ;
    public Response<Boolean> getOrderByUserId(Long userId) ;
}
