package com.defi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defi.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	public Customer findByUserName(String username);

}
