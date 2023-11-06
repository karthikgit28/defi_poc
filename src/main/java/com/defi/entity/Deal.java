package com.defi.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class Deal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DealId")
	private int dealId;
	private int assetId;
	private int cashRichOfferId;
	@Column(name = "InterestRate")
	private double interestRate;
	@Column(name = "InterestType")
	private String interestType;
	@Column(name = "Duration")
	private int duration;
	@Column(name = "InterestPayoutCycle")
	private String interestPayoutCycle;
	@Column(name = "LoanAmount")
	private int loanAmount;
	@Column(name = "CRStatus")
	private String cRStatus;
	@Column(name = "AOStatus")
	private String aOStatus;
	@Column(name = "RMStatus")
	private String rMStatus;
	@Column (name = "DealStatus")
	private String dealStatus;
	@Column(name = "ApplicationDate")
	private ZonedDateTime applicationDate;
	@Column(name = "ExpiryDate")
	private ZonedDateTime expiryDate;
	@Column (name = "SkuId")
	private Integer skuId;
	@Column (name = "HederaStatus")
	private Integer hederaStatus;
	@Column(insertable=false, updatable=false)
	private ZonedDateTime createdOn;
	@Column(name = "UpdatedOn")
	private ZonedDateTime updatedOn;
	@Column(name = "UpdatedBy")
	private String updatedBy;
	@OneToOne
	@JoinColumn(name = "AssetId",referencedColumnName="AssetId", insertable = false, updatable = false)
	private AssetListing asset;
	@OneToOne
	@JoinColumn(name = "CashRichOfferId",referencedColumnName="CashRichOfferId", insertable = false, updatable = false)
	private CashRichOffer cROffer;

}
