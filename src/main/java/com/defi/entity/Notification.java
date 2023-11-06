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
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int notificationId;
	private String tableName;
	private int customerId;
	private int pkCol;
	private String notificationStatus;
	private String comments;
	@Column(insertable=false, updatable=false)
	private ZonedDateTime createdOn;
	private ZonedDateTime updatedOn;
	private String updatedBy;

}
