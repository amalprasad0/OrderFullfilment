package com.order_service.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.order_service.entity.Orders;
import com.order_service.interfaces.IOrderService;
import com.order_service.models.OrderRequest;
import com.order_service.models.Response;
import com.order_service.repository.OrdersRepository;

public class OrderService implements IOrderService {
    @Autowired
    private OrdersRepository orderRepository;
    @Override
    public Response<Long> createOrder(OrderRequest orderRequest) {
        try {
            if (orderRequest == null) {
                return Response.error("Order request is empty");
            }
            if (orderRequest.getUserId() <= 0) {
                return Response.error("User ID is invalid");
            }
            if (orderRequest.getProductId() == null || orderRequest.getProductId().isEmpty()) {
                return Response.error("Product ID is invalid");
            }
            if (orderRequest.getQuantity() <= 0) {
                return Response.error("Quantity is invalid");
            }
            if (orderRequest.getTotalPrice() <= 0) {
                return Response.error("Total price is invalid");
            }
            if (orderRequest.getDeliveryAddress() == null || orderRequest.getDeliveryAddress().isEmpty()) {
                return Response.error("Delivery address is invalid");
            }
            if (orderRequest.getPaymentMethod() == null || orderRequest.getPaymentMethod().isEmpty()) {
                return Response.error("Payment method is invalid");
            }
            Orders order = new Orders();
            order.setDeliveryAddress(orderRequest.getDeliveryAddress());
            order.setPaymentMethod(orderRequest.getPaymentMethod());
            order.setPaymentStatus(orderRequest.getPaymentStatus());
            order.setProductId(orderRequest.getProductId());
            order.setQuantity(orderRequest.getQuantity());
            order.setTotalPrice(orderRequest.getTotalPrice());
            order.setUserId(orderRequest.getUserId());
            order.setOrderStatus("PENDING");
            var savedOrder = orderRepository.save(order);
            if (savedOrder != null) {
                return Response.success(savedOrder.getId(), "Order created successfully");
            }
            return Response.error("Failed to create order");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("An error occurred while creating the order: " + e.getMessage());
        }
        
    }
}
