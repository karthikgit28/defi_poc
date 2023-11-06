package com.defi.entity;

import java.time.ZonedDateTime;
import java.util.List;

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
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerId;
	private String customerAddress1;
	private String customerAddress2;
	private int bankRelationId;
	private String userName;	
	private String password;	
	private String customerType;
	@Column(insertable=false, updatable=false)
	private ZonedDateTime createdOn;
	private ZonedDateTime updatedOn;
	private String updatedBy;
	
}
