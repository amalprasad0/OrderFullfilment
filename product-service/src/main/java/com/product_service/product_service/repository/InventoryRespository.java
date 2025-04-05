package com.product_service.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product_service.product_service.entity.Inventory;

public interface InventoryRespository extends JpaRepository<Inventory, Long> {
   

}
