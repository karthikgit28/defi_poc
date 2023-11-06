package com.defi.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@DynamicUpdate
@Table(name = "AssetListing",schema = "ARDEFI")
public class AssetListing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AssetId")
	private int assetId;
	@Column(name = "CustomerId")
	private int customerId;
	private int categoryId;
	@Column(name = "AssetName")
	private String assetName;
	@Column(name = "NFTTokenId")
	private int nFTTokenId;
	@Column(name = "LoanRequested")
	private double loanRequested;
	@Column(name = "AssetPrice")
	private double assetPrice;
	@Column(name = "ColleteralizationOffer")
	private int colleteralizationOffer;
	@Column(name ="Eligibility")
	private String eligibility;	
	@Column (name = "NFTAssetId")
	private String nFTAssetId;
	@Column(insertable=false, updatable=false,name = "CreatedOn")
	private ZonedDateTime createdOn;
	@Column(name = "UpdatedOn")
	private ZonedDateTime updatedOn;
	@Column(name = "UpdatedBy")
	private String updatedBy;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AssetId",referencedColumnName="AssetId")
	private PaymentTerms paymentTerms;
	@ManyToOne
	@JoinColumn(name= "CategoryId", insertable = false, updatable = false)
	private Category category;
	@ManyToOne
	@JoinColumn(name= "CustomerId", insertable = false, updatable = false)
	private Customer customer;

}
