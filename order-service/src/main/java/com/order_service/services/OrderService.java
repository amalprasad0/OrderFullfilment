package com.order_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order_service.entity.OrderPaymentMap;
import com.order_service.entity.Orders;
import com.order_service.interfaces.IOrderService;
import com.order_service.models.OrderCancelRequest;
import com.order_service.models.OrderRequest;
import com.order_service.models.Response;
import com.order_service.repository.OrderPaymentRepository;
import com.order_service.repository.OrdersRepository;


@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrdersRepository orderRepository;
    @Autowired
    private OrderPaymentRepository orderPayementRepository;
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
            if (orderRequest.getPaymentStatus() == null || orderRequest.getPaymentStatus().isEmpty()) {
                return Response.error("Payment status is invalid");
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
            OrderPaymentMap orderPaymentMap = new OrderPaymentMap();
            orderPaymentMap.setPaymentMethod(orderRequest.getPaymentMethod());
            orderPaymentMap.setPaymentStatus(orderRequest.getPaymentStatus());
            orderPaymentMap.setOrderId(order);
            orderPaymentMap.setPaymentResponse("PENDING");
            orderPaymentMap.setPaymentDate(orderRequest.getOrderDateTime().toString());
            orderPaymentMap.setPaymentId(0);
            var savedOrder = orderRepository.save(order);
            var savedOrderPayment = orderPayementRepository.save(orderPaymentMap);
            if (savedOrderPayment == null) {
                return Response.error("Failed to create order payment map");
            }
            if (savedOrder != null) {
                return Response.success(savedOrder.getId(), "Order created successfully");
            }
           

            return Response.error("Failed to create order");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("An error occurred while creating the order: " + e.getMessage());
        }
        
    }
    @Override
    public Response<Boolean> canceledOrder(OrderCancelRequest orderCancelRequest) {
        try {
            if (orderCancelRequest.orderId == null || orderCancelRequest.orderId <= 0) {
                return Response.error("Order ID is invalid");
            }
            var order = orderRepository.findById(orderCancelRequest.orderId);

            if (order.isPresent()) {
                Orders orders = order.get();
                orders.setOrderStatus("CANCELED");
                orderRepository.save(orders);
                return Response.success(true, "Order canceled successfully");
            } else {
                return Response.error("Order not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("An error occurred while canceling the order: " + e.getMessage());
        }
    }
}
