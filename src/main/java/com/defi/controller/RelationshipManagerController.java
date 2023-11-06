package com.defi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.defi.model.AssetListingRO;
import com.defi.model.CashRichOfferRO;
import com.defi.model.PaymentTermsRO;
import com.defi.service.AssetListingService;
import com.defi.service.CashRichOfferService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/rm")
@CrossOrigin
public class RelationshipManagerController {

	@Autowired
	private AssetListingService service;
	
	@Autowired
	private CashRichOfferService cRService;
	
	@PutMapping("/updatepaymentterms")
	@CrossOrigin
	public AssetListingRO updateRMPaymentTerms(@Valid @RequestBody PaymentTermsRO paymentTerms) {
		return service.updateRMPaymentTerms(paymentTerms);
	}
	
	@PutMapping("/updatecro")
	@CrossOrigin
	public CashRichOfferRO updateRMCRO(@Valid @RequestBody CashRichOfferRO cRO) {
		return cRService.updateRMPaymentTerms(cRO);
	}
	
	@GetMapping(value = "/fetchValidationPendingAssets", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public List<AssetListingRO> getValidationPendingAsset()    
	{  
		return service.fetchValidationPendingAssets();  
	} 
}
