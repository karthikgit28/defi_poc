package com.defi.hedera;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitiateLoanContractRequest {
	
	private String contractId;
    private String assetTokenId;
    private String assetId;
    private String assetDescription;
    private String loanAmount;
    private String dueAmount;
    private String aoUserId;
    private String crUserId;
    private String period;
    private String state;
    private String publish;

}
