package com.defi.model;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AssetListingRO {
	
	private int assetId;
	private int customerId;
	private String assetName;
	@JsonProperty("nFTTokenId")
	private int nFTTokenId;
	@JsonProperty("nFTAssetId")
	private String nFTAssetId;
	private int categoryId;
	private double loanRequested;
	private double assetPrice;
	private int colleteralizationOffer;
	@JsonProperty("createdOn")
	private ZonedDateTime createdOn;
	private String eligibility;
	private Integer notificationId;
	private PaymentTermsRO paymentTerms;
	private CategoryRO category;
	private CustomerRO customer;

}
