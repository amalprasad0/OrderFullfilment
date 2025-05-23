package com.order_service.interfaces;

import java.util.List;

import com.order_service.models.OrderCancelRequest;
import com.order_service.models.OrderDetails;
import com.order_service.models.OrderRequest;
import com.order_service.models.OrderResponse;
import com.order_service.models.OrderUpdateRequest;
import com.order_service.models.Response;

public interface IOrderService {
    public  Response<OrderResponse> createOrder(OrderRequest orderRequest) ;
    public Response<Boolean> canceledOrder(OrderCancelRequest orderCancelRequest) ;
    public Response<Boolean> updateOrder(OrderUpdateRequest orderUpdateRequest) ;
     public Response<OrderDetails> getOrderById(Long orderId) ;
     Response<List<OrderDetails>> getOrderByUserId(Long userId) ;
}
