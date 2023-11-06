package com.defi.hedera;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CreateNFTTokenRequest {
	
	private String ipfcid;
	private String tokenName;
	private String tokenSymbol;
	private String supplyType;
	private int maxSupply;
	private double decimals;
	private String wipeKey;
	private String freezeKey;
	private String adminKey;
	private String pauseKey;

}
