package com.order_service.interfaces;

import com.order_service.models.OrderStatusUpdateRequest;
import com.order_service.models.Response;

public interface IOrderPhaseService {
    public Response<Boolean> updateOrderStatus(OrderStatusUpdateRequest orderStatusUpdateRequest) ;
}
