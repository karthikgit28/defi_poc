package com.defi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.defi.model.CustomerRO;
import com.defi.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin
public class CustomerController {
	
	@Autowired
	private CustomerService service;

	@PostMapping("/login")
	@CrossOrigin
	public CustomerRO createAsset(@Valid @RequestBody CustomerRO customer) {
		return service.login(customer);
	}
	
	@PutMapping("/enable/investor/{customerid}")
	@CrossOrigin
	public CustomerRO enableInvestorMode(@PathVariable int customerid) {
		return service.enableInvestorMode(customerid);
	}
	
	@PostMapping("/register")
	@CrossOrigin
	public CustomerRO createCustomer(@Valid @RequestBody CustomerRO customer) {
		return service.createCustomer(customer);
	}

}
