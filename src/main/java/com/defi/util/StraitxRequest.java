package com.defi.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StraitxRequest {
	
	private String registrationType;
	private String customerName;
	private String registrationId;
	private String dateOfBirth;
	private String countryOfResidence;
	private String nationality;
	private String gender;
	private String email;
	private String phoneNo;
	private String accountNumber;
	private String bank;
	private String accountHolderName;
	private String verificationStatus;
	
	private String account_no;
	private String account_holder_name;
	private StraitxAccountProof bank_account_proof;
	
	private String destination_bank_account_no;
	private String destination_bank_short_code;
	private String amount;
	private String source_bank_account_holder_name;
	private String end_to_end_ref;
	
	
	
	
	
	
		  

}
