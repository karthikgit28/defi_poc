package com.defi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.defi.util.StraitxRequest;
import com.defi.util.StraitxResponse;

import reactor.core.publisher.Mono;

@Service
public class StraitxService {
	
	@Autowired
	@Qualifier("StraitX")
	private WebClient webClient;
	
	
	public String createCustomerProfile(StraitxRequest request) {
		String response = webClient.post()
		.uri("/kyc/customer_profiles")
		//.header("X-XFERS-APP-API-KEY", "6s4czijt5ThNAAMBKkxzYMyeS9zz_PrHWdNZ8W4Vh_x")
		//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.accept(MediaType.APPLICATION_JSON)
		.body(Mono.just(request),StraitxRequest.class)
		.retrieve()
		.bodyToMono(String.class)
		.block();
		
		return response;
		
	}
	
	public StraitxResponse[] addBankAccount(StraitxRequest request,String customerId) {
//		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//		formData.add("account_no", accountNumber);
//		formData.add("bank", bank);
//		formData.add("account_holder_name", holderName);
		StraitxResponse[] response = webClient.post()
		//.uri("/customer_profile/"+customerId+"/bank_accounts")
		.uri(t -> t.path("/customer_profile/{customerId}/bank_accounts")
						.build(customerId))
		//.header("X-XFERS-APP-API-KEY", "6s4czijt5ThNAAMBKkxzYMyeS9zz_PrHWdNZ8W4Vh_x")
		//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.accept(MediaType.APPLICATION_JSON)
		.body(Mono.just(request),StraitxRequest.class)
		.retrieve()
		.bodyToMono(StraitxResponse[].class)
		.block();
		
		return response;
		
	}
	
	public StraitxResponse[] fetchBankAccount(String customerId) {
		StraitxResponse[] response = webClient.get()
		//.uri("/customer_profile/"+customerId+"/bank_accounts")
		.uri(t -> t.path("/customer_profile/{customerId}/bank_accounts")
						.build(customerId))
		//.header("X-XFERS-APP-API-KEY", "6s4czijt5ThNAAMBKkxzYMyeS9zz_PrHWdNZ8W4Vh_x")
		//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.bodyToMono(StraitxResponse[].class)
		.block();
		
		return response;
		
	}
	
	public ResponseEntity<StraitxResponse> updateBankAccount(StraitxRequest request) {
		ResponseEntity<StraitxResponse> response = webClient.get()
				.uri(t -> t.path("/customer_profile/$1/bank_accounts/$2")
						.build(1,2))
		//.header("X-XFERS-APP-API-KEY", "6s4czijt5ThNAAMBKkxzYMyeS9zz_PrHWdNZ8W4Vh_x")
		//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.toEntity(StraitxResponse.class)
		.block();
		
		return response;
		
	}
	
	
	public String verifyBankAccount(String status,String customerId,String bankAccountId) {
		String response = webClient.put()
//				.uri(t -> t.path("/customer_profile/$1/bank_accounts/$2")
//						.build(1,2))
				//.uri("/customer_profile/{customer_profile_id}/bank_accounts/{bank_account_id}",customerId,bankAccountId)
				.uri(uriBuilder -> uriBuilder.path("/sandbox/customer_profile/{customer_profile_id}/bank_accounts/{bank_account_id}")
						.queryParam("verification_status", status)
						.build(customerId,bankAccountId))
		//.header("X-XFERS-APP-API-KEY", "6s4czijt5ThNAAMBKkxzYMyeS9zz_PrHWdNZ8W4Vh_x")
		//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.accept(MediaType.APPLICATION_JSON)
		//.body(Mono.just(request),StraitxRequest.class)
		.retrieve()
		.bodyToMono(String.class)
		.block();
		
		return response;
		
	}
	
	
	public String makeTransfer(StraitxRequest request,String customerId) {
		//StraitxRequest request = new StraitxRequest();
//		request.setDestination_bank_account_no(destinationAccount);
//		request.setDestination_bank_short_code(destinationBankCode);
//		request.setAmount(amount);
//		request.setSource_bank_account_holder_name(sourceAccount);
		String response = webClient.post()
				.uri(t -> t.path("/sandbox/customer_profile/{customer_profile_id}/bank_transfer_simulations")
						.build(customerId))
//		.uri(uriBuilder -> uriBuilder
//			    .path("/sandbox/user/bank_transfer_simulations")
//			    .queryParam("destination_bank_account_no", destinationAccount)
//			    .queryParam("destination_bank_short_code", destinationBankCode)
//			    .queryParam("source_bank_account_holder_name", sourceAccount)
//			    .queryParam("amount", Integer.parseInt(amount))
//			    .build())
		//.header("X-XFERS-APP-API-KEY", "6s4czijt5ThNAAMBKkxzYMyeS9zz_PrHWdNZ8W4Vh_x")
		//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.accept(MediaType.APPLICATION_JSON)
		.body(Mono.just(request),StraitxRequest.class)
		.retrieve()
		.bodyToMono(String.class)
		.block();
		
		return response;
		
	}
	
	

}
