package com.order_service.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.order_service.entity.OrderPaymentMap;
import com.order_service.entity.Orders;
import com.order_service.feign.ProductClient;
import com.order_service.interfaces.IOrderService;
import com.order_service.models.OrderCancelRequest;
import com.order_service.models.OrderDetails;
import com.order_service.models.OrderRequest;
import com.order_service.models.OrderUpdateRequest;
import com.order_service.models.Response;
import com.order_service.repository.OrderPaymentRepository;
import com.order_service.repository.OrdersRepository;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrdersRepository orderRepository;
    @Autowired
    private OrderPaymentRepository orderPayementRepository;
    @Autowired
    private ProductClient productClient;

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

    @Override
    @CachePut(cacheNames = "order", key = "#p0.userId")
    public Response<Boolean> updateOrder(OrderUpdateRequest orderUpdateRequest) {
        try {
            if (orderUpdateRequest == null) {
                return Response.error("Order update request is empty");
            }
            if (orderUpdateRequest.getOrderId() <= 0) {
                return Response.error("Order ID is invalid");
            }
            if (orderUpdateRequest.getDeliveryAddress() == null || orderUpdateRequest.getDeliveryAddress().isEmpty()) {
                return Response.error("Delivery address is invalid");
            }
            if (orderUpdateRequest.getPaymentMethod() == null || orderUpdateRequest.getPaymentMethod().isEmpty()) {
                return Response.error("Payment method is invalid");
            }
            if (orderUpdateRequest.getPaymentStatus() == null || orderUpdateRequest.getPaymentStatus().isEmpty()) {
                return Response.error("Payment status is invalid");
            }

            var order = orderRepository.findById(orderUpdateRequest.getOrderId());
            if (order.isPresent()) {
                Orders orders = order.get();
                orders.setDeliveryAddress(orderUpdateRequest.getDeliveryAddress());
                orders.setPaymentMethod(orderUpdateRequest.getPaymentMethod());
                orders.setPaymentStatus(orderUpdateRequest.getPaymentStatus());
                orders.setProductId(orderUpdateRequest.getProductId());
                orders.setQuantity(orderUpdateRequest.getQuantity());
                orders.setTotalPrice(orderUpdateRequest.getTotalPrice());
                orders.setUserId(orderUpdateRequest.getUserId());
                // orders.setOrderStatus("PENDING");
                var savedOrder = orderRepository.save(orders);
                if (savedOrder != null) {
                    return Response.success(true, "Order updated successfully");
                } else {
                    return Response.error("Failed to update order");
                }
            } else {
                return Response.error("Order not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("An error occurred while updating the order: " + e.getMessage());
        }
    }

    @Override
    @Cacheable(value = "order", key = "#orderId")

    public Response<OrderDetails> getOrderById(Long orderId) {
        try {
            if (orderId == null || orderId <= 0) {
                return Response.error("Order ID is invalid");
            }
            var order = orderRepository.findById(orderId);
            if (order.isPresent()) {
                ResponseEntity<Map<String, Object>> productResponse = productClient
                        .getProductById(Long.parseLong(order.get().getProductId()));
                Map<String, Object> responseBody = productResponse.getBody();
                boolean success = (boolean) responseBody.get("success");
                if (!success)
                    return Response.error("Couldn't find the Product Details");
                Map<String, Object> productData = (Map<String, Object>) responseBody.get("data");

                String productName = (String) productData.get("productName");
                String productDescription = (String) productData.get("productDescription");
                String productImageUrl = (String) productData.get("productImageUrl");
                String brand = (String) productData.get("brand");

                @SuppressWarnings("unchecked")
                Map<String, Object> productCategory = (Map<String, Object>) productData.get("productCategory");
                String categoryName = (String) productCategory.get("categoryName");
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderId(orderId);
                orderDetails.setOrderDate(order.get().getOrderDate());
                orderDetails.setDeliveryAddress(order.get().getDeliveryAddress());
                orderDetails.setOrderStatus(order.get().getOrderStatus());
                // orderDetails.getPaymentStatus(order.get().)
                orderDetails.setProductId(Long.parseLong(order.get().getProductId()));
                orderDetails.setProductName(productName);
                orderDetails.setProductImgUrl(productImageUrl);
                orderDetails.setQuantity(order.get().getQuantity());
                orderDetails.setUserId(order.get().getUserId());

                System.out.println("response" + productResponse);
                return Response.success(orderDetails, "Order found successfully");
            } else {
                return Response.error("Order not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("An error occurred while fetching the order: " + e.getMessage());
        }
    }

    @Override
    @Cacheable(value = "order", key = "#userId")

    public Response<OrderDetails> getOrderByUserId(Long userId) {
        try {
            if (userId == null || userId <= 0) {
                return Response.error("User ID is invalid");
            }
            Orders order = orderRepository.findAll().stream()
                    .filter(o -> o.getUserId() == userId)
                    .findFirst()
                    .orElse(null);
            if (order != null) {
                ResponseEntity<Map<String, Object>> productResponse = productClient
                        .getProductById(Long.parseLong(order.getProductId()));
                Map<String, Object> responseBody = productResponse.getBody();
                boolean success = (boolean) responseBody.get("success");
                if (!success)
                    return Response.error("Couldn't find the Product Details");

                @SuppressWarnings("unchecked")
                Map<String, Object> productData = (Map<String, Object>) responseBody.get("data");

                String productName = (String) productData.get("productName");
                String productImageUrl = (String) productData.get("productImageUrl");
                @SuppressWarnings("unchecked")
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderId(order.getId());
                orderDetails.setOrderDate(order.getOrderDate());
                orderDetails.setDeliveryAddress(order.getDeliveryAddress());
                orderDetails.setOrderStatus(order.getOrderStatus());
                orderDetails.setProductId(Long.parseLong(order.getProductId()));
                orderDetails.setProductName(productName);
                orderDetails.setProductImgUrl(productImageUrl);
                orderDetails.setQuantity(order.getQuantity());
                orderDetails.setUserId(order.getUserId());

                System.out.println("response" + productResponse);
                return Response.success(orderDetails, "Order found successfully");
            } else {
                return Response.error("Order not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("An error occurred while fetching the order: " + e.getMessage());
        }
    }
}
