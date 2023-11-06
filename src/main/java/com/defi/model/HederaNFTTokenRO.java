package com.defi.model;

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
public class HederaNFTTokenRO {
	
	private int nFTTokenId;
	private int customerId;
	private String assetName;
	private String tokenId;
	private String tokenStatus;
	private int tokenSerial;
	private String address;
	private String supplyKey;

}
