package com.order_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.order_service.feign.ProductClient;
import com.order_service.interfaces.IOrderService;
import com.order_service.models.OrderCancelRequest;
import com.order_service.models.OrderDetails;
import com.order_service.models.OrderRequest;
import com.order_service.models.OrderUpdateRequest;
import com.order_service.models.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {
    private final IOrderService orderService;
    
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("createOrder")
    public ResponseEntity<Response<Long>> createOrder(@RequestBody OrderRequest entity) {
        return ResponseEntity.ok(orderService.createOrder(entity));
    }
    @PostMapping("canceledOrder")
    public ResponseEntity<Response<Boolean>> orderCancel(@RequestBody OrderCancelRequest orderCancelRequest) {
        return ResponseEntity.ok(orderService.canceledOrder(orderCancelRequest));
    }
    @PostMapping("updateOrder")
    public ResponseEntity<Response<Boolean>> updateOrder(@RequestBody OrderUpdateRequest orderUpdateRequest) {
        return ResponseEntity.ok(orderService.updateOrder(orderUpdateRequest));
    }
    @PostMapping("getOrderById")
    public ResponseEntity<Response<OrderDetails>> getOrderById(@RequestParam Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
    @PostMapping("getOrderByUserId")
    public ResponseEntity<Response<Boolean>> getOrderByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(orderService.getOrderByUserId(userId));
    }
}
