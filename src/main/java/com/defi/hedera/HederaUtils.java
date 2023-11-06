package com.defi.hedera;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class HederaUtils {
	@Autowired
	@Qualifier("Hedera")
	private WebClient webClient;
	
	
	public CreateNFTTokenResponse createNFTToken (CreateNFTTokenRequest nftTokenRequest) {
		
		return webClient.post()
				.uri("https://2v445l6fn75glpuq2nuwihq4wu0tmzjk.lambda-url.ap-southeast-1.on.aws/createnfttoken")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(nftTokenRequest),CreateNFTTokenRequest.class)
				.retrieve()
				.bodyToMono(CreateNFTTokenResponse.class)
				.block();
	}
	
	public Map<String,String> addLoanDetails (AddLoanRequest loanRequest) {
		return webClient.post()
				.uri("https://37rou5ocah2evdfzzdy2kp7ykm0renvk.lambda-url.ap-southeast-1.on.aws/addloandetails")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(loanRequest),AddLoanRequest.class)
				.retrieve()
				.bodyToMono(HashMap.class)
				.block();
	}
	
	public LoanContractResponse initiateLoanContractResponse (InitiateLoanContractRequest contractReq) {
		return webClient.post()
				.uri("https://37rou5ocah2evdfzzdy2kp7ykm0renvk.lambda-url.ap-southeast-1.on.aws/addasset")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(contractReq),InitiateLoanContractRequest.class)
				.retrieve()
				.bodyToMono(LoanContractResponse.class)
				.block();
		
	}
	
	public Map<String,String> loanChangeState (Map<String,String> changeState) {
		return webClient.post()
				.uri("https://37rou5ocah2evdfzzdy2kp7ykm0renvk.lambda-url.ap-southeast-1.on.aws/statechange")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(changeState),Map.class)
				.retrieve()
				.bodyToMono(HashMap.class)
				.block();
	}

}
