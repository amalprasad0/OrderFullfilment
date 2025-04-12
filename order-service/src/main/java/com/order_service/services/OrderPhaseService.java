package com.order_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order_service.entity.Orders;
import com.order_service.interfaces.IOrderPhaseService;
import com.order_service.models.OrderStatusUpdateRequest;
import com.order_service.models.Response;
import com.order_service.repository.OrdersRepository;

@Service
public class OrderPhaseService implements IOrderPhaseService {

    @Autowired
    private OrdersRepository orderRepository;

    @Override
    public Response<Boolean> updateOrderStatus(OrderStatusUpdateRequest orderStatusUpdateRequest) {
        try {
            if (orderStatusUpdateRequest == null) {
                return Response.error("Order status update request is empty");
            }

            if (orderStatusUpdateRequest.getOrderId() <= 0) {
                return Response.error("Order ID is invalid");
            }

            Orders order = orderRepository.findById(orderStatusUpdateRequest.getOrderId()).orElse(null);

            if (order == null) {
                return Response.error("Order not found");
            }

            order.setOrderStatus(orderStatusUpdateRequest.getStatus());

            orderRepository.save(order);

            return Response.success(true, "Order status updated successfully");

        } catch (Exception e) {
            return Response.error("Failed to update order status: " + e.getMessage());
        }
    }
}
