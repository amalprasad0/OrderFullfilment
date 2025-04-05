package com.product_service.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product_service.product_service.entity.StockReservations;

public interface StockReservationRepository extends JpaRepository<StockReservations, Long> {
    // Custom query methods can be defined here if needed
    
}
