package com.defi.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
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

import com.amazonaws.util.CollectionUtils;
import com.defi.entity.Deal;
import com.defi.model.DealRO;
import com.defi.repository.DealRepository;
import com.defi.service.DealService;

@RestController
@RequestMapping("/api/v1/deal")
@CrossOrigin
public class DealController {

	@Autowired
	private DealService service;
	
	@Autowired
	private DealRepository dealRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/initiatedeal")
	@CrossOrigin
	public DealRO createDeal(@RequestBody DealRO deal) {
		return service.createDeal(deal);
	}
	
	@PutMapping("/updatestatus/{dealId}")
	@CrossOrigin
	public DealRO updateCustomerStatus(@PathVariable int dealId,@RequestParam(name = "customerType", required = true) String customerType, @RequestParam(name = "customerStatus", required = true) String customerStatus) {
		return service.updateCustomerStatus(dealId, customerType,customerStatus);
	}
	
	@PutMapping("/updateRMstatus/{dealId}")
	@CrossOrigin
	public DealRO updateRMPaymentTerms(@PathVariable int dealId, @RequestParam(name = "customerStatus", required = true) String customerStatus) {
		return service.updateRMStatus(dealId, customerStatus);
	}
	
	@GetMapping(value = "/fetchdeal/{dealId}", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public DealRO getDealById(@PathVariable int dealId)    
	{  
		return service.fetchDealById(dealId);  
	} 
	
	@GetMapping(value = "/fetchActiveExpiringdeals", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public List<DealRO> getActiveExpiringDeals()    
	{  
		return service.fetchActiveExpiringDeals();  
	} 
	
	@GetMapping(value = "/fetchDealsByStatus", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public List<DealRO> getDeals(@RequestParam(name = "dealStatus", required = true) String dealStatus)    
	{  
		List<DealRO> dealROList = new ArrayList<>();
		List<Deal> dealList = dealRepo.findByDealStatus(dealStatus);
		if (!CollectionUtils.isNullOrEmpty(dealList)) {
			dealList.forEach(f -> {
				DealRO deal = new DealRO();
				deal = mapper.map(f, DealRO.class);
				dealROList.add(deal);
			});
		}

		return dealROList;
	} 
	
}

