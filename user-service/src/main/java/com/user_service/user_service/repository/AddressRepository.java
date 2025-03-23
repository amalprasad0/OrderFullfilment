package com.user_service.user_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.user_service.user_service.entity.Addresses;
public interface AddressRepository  extends JpaRepository<Addresses, Long> {

}
