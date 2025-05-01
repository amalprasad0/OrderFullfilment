package com.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment_service.entity.UserPayment;

public interface UserPayamentRespo extends JpaRepository<UserPayment, Long> {

}
