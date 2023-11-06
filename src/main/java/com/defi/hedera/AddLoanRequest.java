package com.defi.hedera;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddLoanRequest {
	private String assetTokenId;
	private String assetDescription;
	private String loanAmount;
	private String dueAmount;
	private String aoUserId;
	private String period;
}
