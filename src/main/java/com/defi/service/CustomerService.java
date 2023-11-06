package com.defi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defi.entity.Customer;
import com.defi.model.*;
import com.defi.repository.CustomerRepository;


@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private HederaService hedService;
	
	public CustomerRO login(CustomerRO customer) {
		Customer customerDO = repo.findByUserName(customer.getUserName());
		CustomerRO cust = new CustomerRO();
		if (customerDO.getPassword().equals(customer.getPassword())) {
			cust = mapper.map(customerDO, CustomerRO.class);
			cust.setPassword(null);
		}
		return cust;
	}

	public CustomerRO enableInvestorMode(int customerid) {
		Customer customerDO = repo.findById(customerid).get();
		customerDO.setCustomerType("CRO");
		return mapper.map(repo.save(customerDO), CustomerRO.class);
	}
	
	public CustomerRO createCustomer (CustomerRO customer) {
		Customer cust = mapper.map(customer, Customer.class);
		Customer persistedCust = repo.save(cust);
		createNFTToken("Mercedes Benz S-Class",persistedCust.getCustomerId());
		createNFTToken("Sea Breeze Apartments",persistedCust.getCustomerId());
		return mapper.map(persistedCust, CustomerRO.class);
	}
	
	private void createNFTToken (String assetName, int cId) {
		HederaNFTTokenRO hedNFTToken = new HederaNFTTokenRO();
		hedNFTToken.setAssetName(assetName);
		hedNFTToken.setCustomerId(cId);
		hedService.createNFTToken(hedNFTToken);
	}

}
