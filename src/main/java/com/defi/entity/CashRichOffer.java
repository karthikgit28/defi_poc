package com.defi.entity;


import java.time.ZonedDateTime;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class CashRichOffer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cashRichOfferId;
	private int customerId;
//	private double desiredInterestRate;
//	private String desiredInterestType;
//	private int desiredDuration;
//	private String desiredInterestPayoutCycle;
	private int desiredAmount;
	private String eligibility;	
	private double rMInterestRate;
	private String rMInterestType;
	private int rMDuration;
	private String rMInterestPayoutCycle;
	private String rMStatus;
	private String cRStatus;
	@Column(insertable=false, updatable=false)
	private ZonedDateTime createdOn;
	private ZonedDateTime updatedOn;
	private String updatedBy;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "CashRichOfferId")
	private List<CRSelfAssessment> selfAssessment;
	@ManyToOne
	@JoinColumn(name= "CustomerId", insertable = false, updatable = false)
	private Customer customer;
}
