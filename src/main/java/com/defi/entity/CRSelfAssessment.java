package com.defi.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class CRSelfAssessment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cRSelfAssessmentId;
	private int categoryId;
	private double allocationPercentage;
	@Column(insertable=false, updatable=false)
	private ZonedDateTime createdOn;
	private ZonedDateTime updatedOn;
	private String updatedBy;
	@ManyToOne
	@JoinColumn(name= "CategoryId", insertable = false, updatable = false)
	private Category category;
}
