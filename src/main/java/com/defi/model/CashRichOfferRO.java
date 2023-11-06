package com.defi.model;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CashRichOfferRO {
	private int cashRichOfferId;
//	@Positive(message="customerId is Mandatory")
	@JsonProperty("customerId")
	private int customerId;
//	@Positive(message="desiredInterestRate is Mandatory")
//	private double desiredInterestRate;
//	@NotEmpty(message="desiredInterestType is Mandatory")
//	private String desiredInterestType;
//	@Positive(message="desiredDuration is Mandatory")
//	private int desiredDuration;
//	@NotEmpty(message="desiredInterestPayoutCycle is Mandatory")
//	private String desiredInterestPayoutCycle;
//	@Positive(message="desiredAmount is Mandatory")
	private int desiredAmount;
	private String eligibility;
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
	@JsonProperty("cRStatus")
	private String cRStatus;
	private List<CRSelfAssessmentRO> selfAssessment;
	private Integer notificationId;
	@JsonProperty("createdOn")
	private ZonedDateTime createdOn;
	private CustomerRO customer;

}
