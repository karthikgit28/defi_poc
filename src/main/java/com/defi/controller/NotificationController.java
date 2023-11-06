package com.defi.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.defi.service.NotificationService;

@RestController
@RequestMapping("/api/v1/notification")
@CrossOrigin
public class NotificationController {
	
	@Autowired
	private NotificationService service;
	
	@GetMapping(value = "/fetchcroffers", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public <T> T  getCRNotification()    
	{  
		return service.fetchNotification("CashRichOffer");  
	} 
	
	@GetMapping(value = "/fetchaolistings", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public <T> T  getAONotification()    
	{  
		return service.fetchNotification("AssetListing");  
	} 
	
	@PutMapping(value = "/updateNotificationStatus")
	@CrossOrigin
	public ResponseEntity<Object> updateInquiryNotification(@RequestParam(name = "notificationId", required = true) int notificationId) {
		service.updateNotificationStatus(notificationId);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("Updated Notification", notificationId);
		return ResponseEntity.status(HttpStatus.OK).body(map);
}
	@GetMapping(value = "/fetchcustomernotifications", produces = MediaType.APPLICATION_JSON_VALUE)  
	@CrossOrigin
	public <T> T  getCustomerNotification(@RequestParam(name = "customerId", required = true) int customerId)    
	{  
		return service.fetchCustomerNotification(customerId);  
	} 

}
