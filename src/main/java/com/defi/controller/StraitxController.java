package com.defi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.defi.service.StraitxService;
import com.defi.util.StraitxRequest;
import com.defi.util.StraitxResponse;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/straitx")
@CrossOrigin
public class StraitxController {
	
	@Autowired
	private StraitxService service;
	
	
	@PostMapping("/createCustomer")
	@CrossOrigin
	public String createCustomerProfile(@Valid @RequestBody StraitxRequest request) {
		 return service.createCustomerProfile(request);
	}
	
	@GetMapping("/fetchBankAccount")
	@CrossOrigin
	public StraitxResponse[] fetchBankAccount(@RequestParam String customerProfile) {
		 return service.fetchBankAccount(customerProfile);	 
		 
	}
	
	@PutMapping("/verifyBankAccount")
	@CrossOrigin
	public String verifyBankAccount(@RequestParam String verificationStatus,@RequestParam String customerProfile,
			@RequestParam String bankAccountId) {
		 return service.verifyBankAccount(verificationStatus,customerProfile,bankAccountId);	 
		 
	}
	
	@PostMapping("/addBankAccount")
	@CrossOrigin
	public StraitxResponse[] addBankAccount(@Valid @RequestBody StraitxRequest request
			,@RequestParam String customerId) {
		 return service.addBankAccount(request,customerId);	 
		 
	}
	
	
	@PostMapping("/transfer")
	@CrossOrigin
	public String makeTransfer(@Valid @RequestBody StraitxRequest request
			,@RequestParam String customerId) {
		  return service.makeTransfer(request,customerId);
		  
		 
	}

}
