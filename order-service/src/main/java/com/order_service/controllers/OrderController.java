package com.order_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order_service.interfaces.IOrderService;
import com.order_service.models.OrderCancelRequest;
import com.order_service.models.OrderRequest;
import com.order_service.models.Response;

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
    public ResponseEntity<Response<Long>> postMethodName(@RequestBody OrderRequest entity) {
        return ResponseEntity.ok(orderService.createOrder(entity));
    }
    @PostMapping("canceledOrder")
    public ResponseEntity<Response<Boolean>> postMethodName(@RequestBody OrderCancelRequest orderCancelRequest) {
        return ResponseEntity.ok(orderService.canceledOrder(orderCancelRequest));
    }
}
