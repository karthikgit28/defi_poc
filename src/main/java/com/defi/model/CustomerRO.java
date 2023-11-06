package com.defi.model;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CustomerRO {
	private int customerId;
	private String customerAddress1;
	private String customerAddress2;
	private int bankRelationId;
	// AO / CRO / RM
	private String customerType;
	private String userName;	
	private String password;
}
