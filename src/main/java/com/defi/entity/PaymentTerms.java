package com.defi.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@DynamicUpdate
@Table(name = "PaymentTerms")
public class PaymentTerms {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int assetId;
//	private double interestRate;
//	private String interestType;
	private int duration;
//	private String interestPayoutCycle;
	private double rMInterestRate;
	private String rMInterestType;
	private int rMDuration;
	private String rMInterestPayoutCycle;
	private String rMStatus;
	private String customerStatus;
	@Column(insertable=false, updatable=false)
	private ZonedDateTime createdOn;
	private ZonedDateTime updatedOn;
	private String updatedBy;
}
