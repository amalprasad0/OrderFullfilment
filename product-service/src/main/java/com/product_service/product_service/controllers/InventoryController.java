package com.product_service.product_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product_service.product_service.interfaces.IStockInventoryService;
import com.product_service.product_service.models.AddInventory;
import com.product_service.product_service.models.DeleteInventory;
import com.product_service.product_service.models.ReserveStock;
import com.product_service.product_service.models.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/v1/inventory")
// @CrossOrigin(origins = "*", allowedHeaders = "*")
public class InventoryController {
    @Autowired
    public IStockInventoryService stockInventoryService;

    @PostMapping("addInventoryStock")
    public ResponseEntity<Response<Long>> AddInventoryStock(
            @RequestBody AddInventory entity,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {

        if ("ADMIN".equalsIgnoreCase(userRole) && userEmail != null && !userEmail.trim().isEmpty()) {
            return ResponseEntity.ok(stockInventoryService.addInventoryStock(entity));
        } else {
            Response<Long> errorResponse = new Response<>();
            errorResponse.setSuccess(false);
            errorResponse.setResponseMessage("Access Restricted");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @PostMapping("reserveStock")
    public ResponseEntity<Response<Boolean>> reserveStock(@RequestBody ReserveStock entity) {
        return ResponseEntity.ok(stockInventoryService.reserveStock(entity));
    }

    @PostMapping("releaseStock")
    public ResponseEntity<Response<Boolean>> releaseStock(@RequestBody ReserveStock entity) {
        // return ResponseEntity.ok(stockInventoryService.releaseStock(entity));
        return ResponseEntity.ok(stockInventoryService.sendtoInventoryQueue(entity));
    }

    @PostMapping("removeInventory")
    public ResponseEntity<Response<Long>> deleteInventor(
            @RequestBody DeleteInventory entity,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {

        if ("ADMIN".equalsIgnoreCase(userRole) && userEmail != null && !userEmail.isEmpty()) {
            return ResponseEntity.ok(stockInventoryService.deleteInventory(entity));
        } else {
            Response<Long> response = new Response<>();
            response.setSuccess(false);
            response.setResponseMessage("Access Restricted");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

}
