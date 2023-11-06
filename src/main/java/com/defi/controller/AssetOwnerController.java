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

import com.defi.entity.Category;
import com.defi.model.AssetListingRO;
import com.defi.repository.CategoryRepository;
import com.defi.service.AssetListingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/asset")
@CrossOrigin
public class AssetOwnerController {
	
	@Autowired
	private AssetListingService service;
	
	@Autowired
	private CategoryRepository category;
	
	@PostMapping("/createAsset")
	@CrossOrigin
	public AssetListingRO createAsset(@Valid @RequestBody AssetListingRO asset) {
		return service.createAsset(asset);
	}
	
	@GetMapping(value = "/fetchAsset", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public List<AssetListingRO> getAssetByIdName(@RequestParam(required = false) int customerId, @RequestParam(required = false) String assetName)    
	{  
		return service.fetchAssetByIdName(customerId,assetName);  
	} 
	 
	
	@GetMapping(value = "/fetchAllAsset", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public List<AssetListingRO> getAllAsset()    
	{  
		return service.fetchAllAsset();  
	} 
	
	@PutMapping("/agree/paymentterms/{assetId}")
	@CrossOrigin
	public AssetListingRO updateRMPaymentTerms(@PathVariable int assetId,@RequestParam(name = "customerStatus", required = true) String customerStatus) {
		return service.updateCustomerPaymentTerms(assetId, customerStatus);
	}
	
	@GetMapping(value = "/fetchValidatedAssets", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public List<AssetListingRO> getValidatedAsset()    
	{  
		return service.fetchValidatedAssets();  
	} 
	
	@GetMapping(value = "/fetchAsset/{assetId}", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public AssetListingRO getAssetById(@PathVariable int assetId)    
	{  
		return service.fetchAssetById(assetId);  
	} 
	
	
	
	@GetMapping(value = "/fetchCategory", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public List<Category> fetchCategory()    
	{  
		return category.findAll();
	} 

}
