package com.order_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order_service.interfaces.IOrderPhaseService;
import com.order_service.models.OrderStatusUpdateRequest;
import com.order_service.models.OrderUpdateRequest;
import com.order_service.models.Response;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/orderphase")
public class PhaseController {
    @Autowired
    private IOrderPhaseService orderPhaseService;
    @PostMapping("updateOrderStatus")
    public ResponseEntity<Response<Boolean>> updateOrderStatus(@RequestBody OrderStatusUpdateRequest entity) {
        return ResponseEntity.ok(orderPhaseService.updateOrderStatus(entity));
    }
}
