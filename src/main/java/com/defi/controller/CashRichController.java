package com.defi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.defi.model.CashRichOfferRO;
import com.defi.service.CashRichOfferService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cashrich")
@CrossOrigin
public class CashRichController {
	
	@Autowired
	private CashRichOfferService service;
	
	@PostMapping("/createCROffer")
	@CrossOrigin
	public CashRichOfferRO createCashRich(@Valid @RequestBody CashRichOfferRO cROfferRO) {
		return service.createCROffer(cROfferRO);
	}
	
	@GetMapping(value = "/croffer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public CashRichOfferRO getCashRichOfferById(@PathVariable int id)    
	{  
		return service.fetchCROfferById(id);  
	} 
	
	@GetMapping(value = "/croffers", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public List<CashRichOfferRO> getCashRichOffers(@RequestParam(name = "eligibility", required = false) String eligibility)    
	{  
		return service.fetchCROffers(eligibility);  
	} 
	
	@GetMapping(value = "/croffer", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public List<CashRichOfferRO> getCashRichOfferByCustomerId(@RequestParam(name = "customerId", required = true) int customerId)   {  
		return service.fetchCROffersByCustomerId(customerId);  
	} 
	
	@PutMapping("/agree/rmterms/{crofferid}")
	@CrossOrigin
	public CashRichOfferRO updateRMPaymentTerms(@PathVariable int crofferid,@RequestParam(name = "customerStatus", required = true) String customerStatus) {
		return service.updateCustomerPaymentTerms(crofferid,customerStatus);
	}


}
