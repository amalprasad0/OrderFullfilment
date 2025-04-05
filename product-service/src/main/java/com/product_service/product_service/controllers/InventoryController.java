package com.product_service.product_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product_service.product_service.interfaces.IStockInventoryService;
import com.product_service.product_service.models.AddInventory;
import com.product_service.product_service.models.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/inventory")
// @CrossOrigin(origins = "*", allowedHeaders = "*")
public class InventoryController {
    @Autowired
    public IStockInventoryService stockInventoryService;

    @PostMapping("addInventoryStock")
    public ResponseEntity<Response<Long>> AddInventoryStock(@RequestBody AddInventory entity) {
        return ResponseEntity.ok(stockInventoryService.addInventoryStock(entity));
    }

}
