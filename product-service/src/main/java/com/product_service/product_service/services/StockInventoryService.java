package com.product_service.product_service.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_service.product_service.entity.Inventory;
import com.product_service.product_service.entity.StockReservations;
import com.product_service.product_service.interfaces.IProductCategory;
import com.product_service.product_service.interfaces.IStockInventoryService;
import com.product_service.product_service.models.AddInventory;
import com.product_service.product_service.models.ReserveStock;
import com.product_service.product_service.models.Response;
import com.product_service.product_service.rabbitMq.RabbitMQProducer;
import com.product_service.product_service.repository.InventoryRespository;
import com.product_service.product_service.repository.ProductRepository;
import com.product_service.product_service.repository.StockReservationRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;

@Service
public class StockInventoryService implements IStockInventoryService {
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public InventoryRespository inventoryRepository;
    @Autowired
    public StockReservationRepository stockReservationRepository;
    @Autowired
    public RabbitMQProducer rabbitMQProducer;
    @Autowired private ObjectMapper objectMapper;

    @Override
    public Response<Long> addInventoryStock(AddInventory entity) {

        try {
            if (entity.getProductId() == null || entity.getProductId() <= 0) {
                return Response.error("Failed to add inventory stock: Product ID is invalid");
            }
            if (entity.getStockAvailable() < 0) {
                return Response.error("Failed to add inventory stock: Stock available cannot be negative");
            }
            var productOpt = productRepository.findById(entity.getProductId());
            if (productOpt.isEmpty()) {
                return Response.error("Failed to add inventory stock: Product ID does not exist");
            }
            var product = productOpt.get();
            Inventory inventory = new Inventory();
            inventory.setProductId(product);
            inventory.setStock_available(entity.getStockAvailable());
            inventory.setStock_reserved(entity.getStockReserved());
            inventory.setCreatedBy(entity.getCreatedBy());
            inventory.setUpdatedBy(entity.getUpdatedBy());
            inventory.setActive(entity.isActive());

            var savedInventory = inventoryRepository.save(inventory);
            var id = savedInventory.getId();
            if (id == null) {
                return Response.error("Failed to add inventory stock: ID is null");
            }
            return Response.success(id, "Inventory stock added successfully");
        } catch (Exception e) {
            return Response.error("Error adding inventory stock" + e.getMessage());
        }
    }

    @Override
    public Response<Boolean> reserveStock(com.product_service.product_service.models.ReserveStock entity) {
        try {
            if (entity.getProductId() == null || entity.getProductId() <= 0) {
                return Response.error("Failed to reserve stock: Product ID is invalid");
            }
            if (entity.getStockReserved() < 0) {
                return Response.error("Failed to reserve stock: Stock reserved cannot be negative");
            }
            var productOpt = productRepository.findById(entity.getProductId());
            if (productOpt.isEmpty()) {
                return Response.error("Failed to reserve stock: Product ID does not exist");
            }
            var product = productOpt.get();
            var inventoryOpt = inventoryRepository.findAll().stream()
                    .filter(i -> i.getProductId().getId().equals(product.getId()))
                    .findFirst();
            if (inventoryOpt.isEmpty()) {
                return Response.error("Failed to reserve stock: Inventory not found for the product");
            }
            StockReservations stockReservations = new StockReservations();
            stockReservations.setProductId(product);
            stockReservations.setStock_reserved(entity.getStockReserved());
            stockReservations.setCreatedBy(entity.getReservedBy());
            stockReservations.setUpdatedBy(entity.getReservedBy());
            stockReservations.setOrderId(entity.getOrderId());
            stockReservations.setStatus("reserved");
            var inventory = inventoryOpt.get();
            var currentAvailableStock = inventory.getStock_available();
            if (currentAvailableStock < entity.getStockReserved()) {
                return Response.error("Failed to reserve stock: Not enough available stock");
            }
            inventory.setStock_available(currentAvailableStock - entity.getStockReserved());
            inventory.setStock_reserved(inventory.getStock_reserved() + entity.getStockReserved());
            var savedInventory = inventoryRepository.save(inventory);
            if (savedInventory == null) {
                return Response.error("Failed to reserve stock: Stock reservation failed");
            }
            stockReservations.setInventoryId(savedInventory);
            var savedStockReservation = stockReservationRepository.save(stockReservations);
            if (savedStockReservation == null) {
                return Response.error("Failed to reserve stock: Stock reservation failed");
            }
            var id = savedInventory.getId();
            if (id == null) {
                return Response.error("Failed to reserve stock: ID is null");
            }
            if (savedInventory.getStock_available() < 0) {
                return Response.error("Failed to reserve stock: Stock available cannot be negative");
            }
            return Response.success(true, "Stock reserved successfully");
        } catch (Exception e) {
            return Response.error("Error reserving stock" + e.getMessage());
        }
    }

    @Override
    public Response<Boolean> releaseStock(ReserveStock entity) {
        try{
            if (entity.getProductId() == null || entity.getProductId() <= 0) {
                return Response.error("Failed to release stock: Product ID is invalid");
            }
            if (entity.getStockReserved() < 0) {
                return Response.error("Failed to release stock: Stock reserved cannot be negative");
            }
            var productOpt = productRepository.findById(entity.getProductId());
            if (productOpt.isEmpty()) {
                return Response.error("Failed to release stock: Product ID does not exist");
            }
            var product = productOpt.get();
            var inventoryOpt = inventoryRepository.findAll().stream()
                    .filter(i -> i.getProductId().getId().equals(product.getId()))
                    .findFirst();
            if (inventoryOpt.isEmpty()) {
                return Response.error("Failed to release stock: Inventory not found for the product");
            }
            var stockReservationOpt = stockReservationRepository.findAll().stream()
                .filter(i -> i.getInventoryId().getId().equals(inventoryOpt.get().getId()))
                .findFirst();
            if (stockReservationOpt.isEmpty()) {
                return Response.error("Failed to release stock: Stock reservation not found for the product");
            }
            StockReservations stockReservations = stockReservationOpt.get();
            stockReservations.setStatus("release");
            var inventory = inventoryOpt.get();
            var currentAvailableStock = inventory.getStock_available();
            if (currentAvailableStock < entity.getStockReserved()) {
                return Response.error("Failed to release stock: Not enough available stock");
            }
            inventory.setStock_available(currentAvailableStock + entity.getStockReserved());
            inventory.setStock_reserved(inventory.getStock_reserved() - entity.getStockReserved());
            var savedInventory = inventoryRepository.save(inventory);
            var releasedStock=stockReservationRepository.save(stockReservations);
            if (savedInventory == null) {
                return Response.error("Failed to release stock: Stock release failed");
            }
            if(releasedStock==null){
                Response.error("Failed to Release Stock");
            }
            var id = savedInventory.getId();
            if (id == null) {
                return Response.error("Failed to release stock: ID is null");
            }
            if (savedInventory.getStock_available() < 0) {
                return Response.error("Failed to release stock: Stock available cannot be negative");
            }
            return Response.success(true, "Stock released successfully");
        }catch(Exception e){
            return Response.error("Error releasing stock" + e.getMessage());
        }
    }
    public Response<Boolean> sendtoInventoryQueue(ReserveStock entity){
        try {
            if (entity.getProductId() == null || entity.getProductId() <= 0) {
                return Response.error("Failed to release stock: Product ID is invalid");
            }
            if (entity.getStockReserved() < 0) {
                return Response.error("Failed to release stock: Stock reserved cannot be negative");
            }
            String releaseStock = objectMapper.writeValueAsString(entity);
            rabbitMQProducer.sendMessage(releaseStock);
            return Response.success(true, "Added to Queue");
        } catch (Exception e) {
            return Response.error("Error while adding to queue");
        }
    }
}
