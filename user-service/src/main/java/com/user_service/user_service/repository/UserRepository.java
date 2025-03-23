package com.user_service.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user_service.user_service.entity.Users;


public interface UserRepository extends JpaRepository<Users, Long> {

}
