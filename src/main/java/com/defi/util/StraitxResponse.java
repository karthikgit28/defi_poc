package com.defi.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class StraitxResponse {


	private String id;
	private String account_no;
	private String account_holder_name;
	private String updated_at;
	private int bank_id;
	private String bank_abbrev;
	private boolean disabled;
	private String verification_status;
	private String reject_reason;
	private Object bank_account_proof;
	private String admin_notes;
	


}
