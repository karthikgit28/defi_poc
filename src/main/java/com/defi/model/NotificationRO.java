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
public class NotificationRO {
	private int notificationId;
	private String notificationStatus;
	private AssetListingRO aOListing;
	private CashRichOfferRO cROffer;
	private int customerId;
	private String comments;
	private DealRO deal;
}
