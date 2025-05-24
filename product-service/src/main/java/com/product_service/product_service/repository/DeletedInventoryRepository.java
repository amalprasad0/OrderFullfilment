package com.product_service.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product_service.product_service.entity.DeleteInventories;

public interface DeletedInventoryRepository  extends JpaRepository<DeleteInventories, Long> {
    
   
}
