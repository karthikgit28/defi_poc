package com.defi.entity;


import java.time.ZonedDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class HederaNFTToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NFTTokenId")
	private int nFTTokenId;
	@Column(name = "CustomerId")
	private int customerId;
	@Column(name = "AssetName")
	private String assetName;
	@Column(name = "TokenId")
	private String tokenId;
	@Column(name = "TokenStatus")
	private String tokenStatus;
	@Column(name = "TokenSerial")
	private int tokenSerial;
	@Column(name = "Address")
	private String address;
	@Column(name = "SupplyKey")
	private String supplyKey;
	@Column(insertable=false, updatable=false)
	private ZonedDateTime createdOn;
	@Column(name = "UpdatedOn")
	private ZonedDateTime updatedOn;
	@Column(name = "UpdatedBy")
	private String updatedBy;

}
