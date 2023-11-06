package com.defi.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.defi.model.HederaNFTTokenRO;
import com.defi.service.HederaService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Slf4j
public class HederaController {
	
	@Autowired
	private HederaService service;
	
	@PostMapping("/hedera/createAsset")
	@CrossOrigin
	public Object createAsset(@RequestBody HederaNFTTokenRO asset) {
		ResponseEntity<Map<String, Object>> response = null;
		Map<String, Object> responseMap = new LinkedHashMap<String, Object>();
		try {
			return service.createNFTToken(asset);
		} catch (WebClientResponseException ex) {
			log.error("HederaController Exception message:{}", ex);
			ex.printStackTrace();
			responseMap.put("status", "ERROR");
			responseMap.put("Data", "Hedera API Call Failure");
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
			return response;
		}
		
	}
	
	@GetMapping(value = "/tokenizedAssets", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public List<HederaNFTTokenRO> getAllAsset(@RequestParam(name = "customerId", required = true) int customerId)    
	{  
		return service.fetchNFTTokensByCustomerId(customerId);  
	} 

}
