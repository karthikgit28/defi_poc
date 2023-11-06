package com.defi.hedera;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanContractResponse {
	private String status;
	private String assetId;
	private int sku;
	private String message;
}
