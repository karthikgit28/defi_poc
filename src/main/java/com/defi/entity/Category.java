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
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;
	private String categoryName;
	private String categoryDesc;
	@Column(insertable=false, updatable=false)
	private ZonedDateTime createdOn;
	private String createdBy;
	private ZonedDateTime updatedOn;
	private String updatedBy;

}
