package com.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order_service.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

}
