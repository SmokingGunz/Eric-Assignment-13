package com.coderscampus.Assignment13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.Assignment13.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository <Address, Long> {
	
	
}


