package com.defi.model;

import java.time.ZonedDateTime;

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
public class DealRO {
	
	private int dealId;
	private int assetId;
	private int cashRichOfferId;
	@JsonProperty("interestRate")
	private double interestRate;
	@JsonProperty("interestType")
	private String interestType;
	@JsonProperty("duration")
	private int duration;
	@JsonProperty("interestPayoutCycle")
	private String interestPayoutCycle;
	@JsonProperty("loanAmount")
	private int loanAmount;
	@JsonProperty("cRStatus")
	private String cRStatus;
	@JsonProperty("aOStatus")
	private String aOStatus;
	@JsonProperty("rMStatus")
	private String rMStatus;
	private String dealStatus;
	@JsonProperty("applicationDate")
	private ZonedDateTime applicationDate;
	@JsonProperty("expiryDate")
	private ZonedDateTime expiryDate;
	private int skuId;
	private int hederaStatus;
	@JsonProperty("createdOn")
	private ZonedDateTime createdOn;
	@JsonProperty("updatedOn")
	private ZonedDateTime updatedOn;
	@JsonProperty("updatedBy")
	private String updatedBy;
	private AssetListingRO asset;
	private CashRichOfferRO cROffer;

}
