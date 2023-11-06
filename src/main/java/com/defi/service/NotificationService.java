package com.defi.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defi.constants.DEFIConstants;
import com.defi.entity.Notification;
import com.defi.model.NotificationRO;
import com.defi.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notifyRepo;

	@Autowired
	private CashRichOfferService cRService;

	@Autowired
	private AssetListingService aoService;
	
	@Autowired
	private DealService dealService;

	public <T> T fetchNotification(String type) {
		List<Notification> notifications = notifyRepo.findByNotificationStatusAndTableNameAndCustomerId("true", type,DEFIConstants.DEFAULT_RM_CUSTOMER_ID);
		List<NotificationRO> notificationsRO = new ArrayList<>();
		notifications.stream().forEach(n -> {
			NotificationRO notification = new NotificationRO();
			notification.setNotificationId(n.getNotificationId());
			notification.setCustomerId(n.getCustomerId());
			notification.setComments(n.getComments());
			if (type.equals("CashRichOffer")) {
				notification.setCROffer(cRService.fetchCROfferById(n.getPkCol()));
				notificationsRO.add(notification);
			} else if (type.equals("AssetListing")) {
				notification.setAOListing(aoService.fetchAssetById(n.getPkCol()));
				notificationsRO.add(notification);
			} else if (type.equals("Deal")) {
				notification.setDeal(dealService.fetchDealById(n.getPkCol()));
				notificationsRO.add(notification);
			}
		});
		return (T) notificationsRO;
	}

	public void updateNotificationStatus(int notificationId) {
		Notification notification = notifyRepo.findById(notificationId).get();
		notification.setNotificationStatus("false");
		notifyRepo.save(notification);
	}
	
	public <T> T fetchCustomerNotification(int customerId) {
		List<Notification> notifications = notifyRepo.findByCustomerId(customerId);
		List<NotificationRO> notificationsRO = new ArrayList<>();
		notifications.stream().forEach(n -> {
			NotificationRO notification = new NotificationRO();
			notification.setNotificationId(n.getNotificationId());
			notification.setCustomerId(n.getCustomerId());
			notification.setComments(n.getComments());
			notification.setNotificationStatus(n.getNotificationStatus());
			if (n.getTableName().equals("CashRichOffer")) {
				notification.setCROffer(cRService.fetchCROfferById(n.getPkCol()));
				notificationsRO.add(notification);
			} else if (n.getTableName().equals("AssetListing")) {
				notification.setAOListing(aoService.fetchAssetById(n.getPkCol()));
				notificationsRO.add(notification);
			} else if (n.getTableName().equals("Deal")) {
				notification.setDeal(dealService.fetchDealById(n.getPkCol()));
				notificationsRO.add(notification);
			}
		});
		Collections.reverse(notificationsRO);
		return (T) notificationsRO;
	}
}
