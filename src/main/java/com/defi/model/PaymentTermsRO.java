package com.defi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PaymentTermsRO {
	private int assetId;
//	private double interestRate;
//	private String interestType;
	private int duration;
//	private String interestPayoutCycle;
	
	@JsonProperty("rMInterestRate")
	private double rMInterestRate;
	
	@JsonProperty("rMInterestType")
	private String rMInterestType;
	
	@JsonProperty("rMDuration")
	private int rMDuration;
	
	@JsonProperty("rMInterestPayoutCycle")
	private String rMInterestPayoutCycle;
	
	@JsonProperty("rMStatus")
	private String rMStatus;
	
	@JsonProperty("customerStatus")
	private String customerStatus;

}
