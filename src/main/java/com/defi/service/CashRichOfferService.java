package com.defi.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defi.constants.DEFIConstants;
import com.defi.entity.CashRichOffer;
import com.defi.entity.Notification;
import com.defi.model.CashRichOfferRO;
import com.defi.repository.CashRichOfferRepository;
import com.defi.repository.NotificationRepository;

@Service
public class CashRichOfferService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CashRichOfferRepository cROfferRepo;

	@Autowired
	private NotificationRepository notifyRepo;

	public CashRichOfferRO createCROffer(CashRichOfferRO cROffer) {
		CashRichOffer cROfferDO = mapper.map(cROffer, CashRichOffer.class);
		cROfferDO.setEligibility("false");
		System.out.println(cROfferDO);
		// Persist CROffer
		CashRichOffer cROfferPersisted = cROfferRepo.save(cROfferDO);
		
		// Set the notification status
		Notification notification = new Notification();
		notification.setPkCol(cROfferDO.getCashRichOfferId());
		notification.setTableName("CashRichOffer");
		notification.setNotificationStatus("true");
		notification.setCustomerId(DEFIConstants.DEFAULT_RM_CUSTOMER_ID);
		notification.setComments("CASHRICH ENROLLED");
		notifyRepo.save(notification);
		return mapper.map(cROfferPersisted, CashRichOfferRO.class);
	}

	public CashRichOfferRO fetchCROfferById(int cROfferId) {
		return mapper.map(cROfferRepo.findById(cROfferId), CashRichOfferRO.class);
	}

	public List<CashRichOfferRO> fetchCROffers(String eligibility) {
		if (eligibility==null || eligibility=="") {
			return cROfferRepo.findAll().stream().map(cr -> mapper.map(cr, CashRichOfferRO.class))
					.collect(Collectors.toList());
		}
		return cROfferRepo.findByEligibility(eligibility).stream().map(cr -> mapper.map(cr, CashRichOfferRO.class))
				.collect(Collectors.toList());
	}
	
	public List<CashRichOfferRO> fetchCROffersByCustomerId(int cId) {
		return cROfferRepo.findByCustomerId(cId).stream().map(cr -> mapper.map(cr, CashRichOfferRO.class))
				.collect(Collectors.toList());
	}
	
	public CashRichOfferRO updateRMPaymentTerms(CashRichOfferRO cRO) {
		
		CashRichOffer cROfferPersisted = cROfferRepo.findById(cRO.getCashRichOfferId()).get();
		
		cROfferPersisted.setRMDuration(cRO.getRMDuration());
		cROfferPersisted.setRMInterestPayoutCycle(cRO.getRMInterestPayoutCycle());
		cROfferPersisted.setRMInterestRate(cRO.getRMInterestRate());
		cROfferPersisted.setRMInterestType(cRO.getRMInterestType());
		cROfferPersisted.setRMStatus(cRO.getRMStatus());
		cROfferPersisted.setUpdatedBy("RM");
		cROfferPersisted.setUpdatedOn(ZonedDateTime.now(ZoneId.of("UTC")));
		
		Notification notification = new Notification();
		notification.setPkCol(cROfferPersisted.getCashRichOfferId());
		notification.setTableName("CashRichOffer");
		notification.setNotificationStatus("true");
		notification.setCustomerId(cROfferPersisted.getCustomerId());
		
		if(cRO.getRMStatus().equalsIgnoreCase(DEFIConstants.RM_STATUS_GO)) {
			cROfferPersisted.setEligibility("true");
			cROfferPersisted.setUpdatedBy("RM");
			cROfferPersisted.setUpdatedOn(ZonedDateTime.now(ZoneId.of("UTC")));
			cROfferRepo.save(cROfferPersisted);
			notification.setComments("RM APPROVED CR");
			// Initiate Block Chain Smart Contract starts here
		} else if (cRO.getRMStatus().equalsIgnoreCase(DEFIConstants.RM_STATUS_NO_GO)) {
			cROfferPersisted.setEligibility("false");
			cROfferRepo.save(cROfferPersisted);
			notification.setComments("RM REJECTED CR");
		}
		
		// Set the notification status
		notifyRepo.save(notification);
		
		return mapper.map(cROfferPersisted, CashRichOfferRO.class);

	}
	
	public CashRichOfferRO updateCustomerPaymentTerms(int cROId, String customerStatus) {

		CashRichOffer cROffer = cROfferRepo.findById(cROId).get();

		cROffer.setUpdatedBy("Customer");
		cROffer.setUpdatedOn(ZonedDateTime.now(ZoneId.of("UTC")));
		cROffer.setCRStatus(customerStatus);
		cROfferRepo.save(cROffer);

		// Set the notification status
		Notification notification = new Notification();
		notification.setPkCol(cROId);
		notification.setTableName("CashRichOffer");
		notification.setNotificationStatus("true");
		notification.setCustomerId(DEFIConstants.DEFAULT_RM_CUSTOMER_ID);
		notification.setComments("Customer responded to the Offer.");
		notifyRepo.save(notification);

		return mapper.map(cROffer, CashRichOfferRO.class);

	}

}
