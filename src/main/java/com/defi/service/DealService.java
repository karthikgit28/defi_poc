package com.defi.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.util.CollectionUtils;
import com.defi.constants.DEFIConstants;
import com.defi.entity.Deal;
import com.defi.entity.Notification;
import com.defi.model.DealRO;
import com.defi.repository.DealRepository;
import com.defi.repository.NotificationRepository;

@Service
public class DealService {

	@Autowired
	private DealRepository dealRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private NotificationRepository notifyRepo;
	
	@Autowired
	private HederaService hedService;

	public DealRO createDeal(DealRO dealRO) {
		dealRO.setExpiryDate(ZonedDateTime.now(ZoneId.of("UTC")).plusMonths(dealRO.getDuration()));
		dealRO.setDealStatus(DEFIConstants.DEAL_STATUS_INITIATED);
		Deal dealPersisted = dealRepo.save(mapper.map(dealRO, Deal.class));

		// Set the notification status
		Notification notification = new Notification();
		notification.setPkCol(dealPersisted.getDealId());
		notification.setTableName("Deal");
		notification.setNotificationStatus("true");
		notification.setCustomerId(DEFIConstants.DEFAULT_RM_CUSTOMER_ID);
		notification.setComments("CR INITIATED LOAN REQUEST");
		notifyRepo.save(notification);
		return mapper.map(dealPersisted, DealRO.class);
	}

	public DealRO fetchDealById(int dealId) {
		try {
			return mapper.map(dealRepo.findById(dealId).get(), DealRO.class);	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new DealRO();
	}

	public DealRO updateRMStatus(int dealId, String status) {
		Deal deal = dealRepo.findById(dealId).get();
		deal.setRMStatus(status);
		deal.setUpdatedBy("RM");
		deal.setUpdatedOn(ZonedDateTime.now(ZoneId.of("UTC")));
		
		// Set the notification status
		if(DEFIConstants.RM_STATUS_GO.equals(status)) {
			// Initial Loan Contract in Hedera
			int sku = hedService.initiateLoanContract(deal);
			deal.setSkuId(sku);
			deal.setDealStatus(DEFIConstants.DEAL_STATUS_VALIDATED);
			// Set Notification for AO
			Notification notificationForAO = new Notification();
			notificationForAO.setPkCol(deal.getDealId());
			notificationForAO.setTableName("Deal");
			notificationForAO.setNotificationStatus("true");
			notificationForAO.setCustomerId(deal.getAsset().getCustomerId());
			notificationForAO.setComments(
					"RM CREATED LOAN AGREEMENT");
			notifyRepo.save(notificationForAO);
		} else if (DEFIConstants.RM_STATUS_NO_GO.equalsIgnoreCase(status)) {
			deal.setDealStatus(DEFIConstants.DEAL_STATUS_CANCELLED);
		}
		Deal dealPersisted = dealRepo.save(deal);
		// Set the notification status for CR
		Notification notificationForCR = new Notification();
		notificationForCR.setPkCol(dealPersisted.getDealId());
		notificationForCR.setTableName("Deal");
		notificationForCR.setNotificationStatus("true");
		notificationForCR.setCustomerId(deal.getCROffer().getCustomerId());
		notificationForCR.setComments(
				"RM CREATED LOAN AGREEMENT");
		notifyRepo.save(notificationForCR);
		return mapper.map(dealPersisted, DealRO.class);
	}
	
	public DealRO updateCustomerStatus (int dealId, String customerType, String customerStatus) {
		
		Deal deal = dealRepo.findById(dealId).get();
		deal.setUpdatedOn(ZonedDateTime.now(ZoneId.of("UTC")));
		if (customerType.equalsIgnoreCase(DEFIConstants.CUSTOMER_TYPE_AO)) {
			deal.setUpdatedBy("AO");
			deal.setAOStatus(customerStatus);
			if (DEFIConstants.CUSTOMER_STATUS_NO_GO.equalsIgnoreCase(customerStatus)) {
				deal.setDealStatus(DEFIConstants.DEAL_STATUS_CANCELLED);
				// Set the notification status for CRO
				Notification notificationForCR = new Notification();
				notificationForCR.setPkCol(deal.getDealId());
				notificationForCR.setTableName("Deal");
				notificationForCR.setNotificationStatus("true");
				notificationForCR.setCustomerId(deal.getCROffer().getCustomerId());
				notificationForCR.setComments(
						"LOAN AGREEMENT CANCELLED BY ASSET OWNER");
				notifyRepo.save(notificationForCR);
				
				// Set the notification status for RM
				Notification notificationForRM = new Notification();
				notificationForRM.setPkCol(deal.getDealId());
				notificationForRM.setTableName("Deal");
				notificationForRM.setNotificationStatus("true");
				notificationForRM.setCustomerId(DEFIConstants.DEFAULT_RM_CUSTOMER_ID);
				notificationForRM.setComments(
						"LOAN AGREEMENT CANCELLED BY ASSET OWNER");
				notifyRepo.save(notificationForRM);
			}
		} else if (customerType.equalsIgnoreCase(DEFIConstants.CUSTOMER_TYPE_CRO)) {
			String hederaStatus = hedService.changeLoanState(deal.getSkuId(), DEFIConstants.HEDERA_LOAN_STATUS_PENDING_CUSTOMER_CONFIRMATION);
			deal.setHederaStatus(Integer.parseInt(hederaStatus));
			deal.setUpdatedBy("CRO");
			deal.setCRStatus(customerStatus);
		}
		
		if (DEFIConstants.CUSTOMER_STATUS_GO.equals(deal.getAOStatus()) && 
				DEFIConstants.CUSTOMER_STATUS_GO.equals(deal.getCRStatus())) {
			// Update the loan status to ACTIVE 
			String hederaStatus = hedService.changeLoanState(deal.getSkuId(), DEFIConstants.HEDERA_LOAN_STATUS_ACTIVE);
			deal.setApplicationDate(ZonedDateTime.now(ZoneId.of("UTC")));
			deal.setExpiryDate(ZonedDateTime.now(ZoneId.of("UTC")).plusMonths(deal.getDuration()));
			deal.setDealStatus(DEFIConstants.DEAL_STATUS_ACTIVE);
			deal.setHederaStatus(Integer.parseInt(hederaStatus));
		}
		dealRepo.save(deal);
		return mapper.map(deal, DealRO.class);
	}
	
	public List<DealRO> fetchActiveExpiringDeals() {
		List<DealRO> dealROList = new ArrayList<>();
		List<Deal> dealList = dealRepo.findByDealStatusAndExpiryDateLessThan(DEFIConstants.DEAL_STATUS_ACTIVE, ZonedDateTime.now(ZoneId.of("UTC")).plusMonths(1));
		if (!CollectionUtils.isNullOrEmpty(dealList)) {
			dealList.forEach(f -> {
				DealRO deal = new DealRO();
				deal = mapper.map(f, DealRO.class);
				dealROList.add(deal);
			});
		}
		return dealROList;
	}
}
