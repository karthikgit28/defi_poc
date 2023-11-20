package com.defi.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defi.constants.DEFIConstants;
import com.defi.entity.AssetListing;
import com.defi.entity.PaymentTerms;
import com.defi.entity.Notification;
import com.defi.model.AssetListingRO;
import com.defi.model.HederaNFTTokenRO;
import com.defi.model.PaymentTermsRO;
import com.defi.repository.AssetListingRepository;
import com.defi.repository.NotificationRepository;
import com.defi.repository.PaymentTermsRepository;

@Service
public class AssetListingService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AssetListingRepository assetRepo;

	@Autowired
	private NotificationRepository notifyRepo;

	@Autowired
	private PaymentTermsRepository paymentRepo;
	
	@Autowired
	private HederaService hedService;

	public AssetListingRO createAsset(AssetListingRO asset) {
		AssetListing assetList = mapper.map(asset, AssetListing.class);
		assetList.setEligibility("false");
		
		try {
			HederaNFTTokenRO token = new HederaNFTTokenRO();
			token.setAssetName(asset.getAssetName());
			HederaNFTTokenRO response = hedService.createNFTToken(token);
			if(response != null) {
				assetList.setNFTTokenId(response.getNFTTokenId());
				//assetList.setNFTAssetId(hedService.addLoanDetails(assetList));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		AssetListing assetListPersisted = assetRepo.save(assetList);
		
		try {
			// Call Hedera To add Loan details the asset. Store the assetId in DB for Deal Creation.
			assetListPersisted.setNFTAssetId(hedService.addLoanDetails(assetListPersisted));
			assetRepo.save(assetListPersisted);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		// Set the notification status
		Notification notification = new Notification();
		notification.setPkCol(assetListPersisted.getAssetId());
		notification.setTableName("AssetListing");
		notification.setNotificationStatus("true");
		notification.setCustomerId(DEFIConstants.DEFAULT_RM_CUSTOMER_ID);
		notification.setComments("ASSET LISTED FOR VALIDATION");
		notifyRepo.save(notification);
		return mapper.map(assetListPersisted, AssetListingRO.class);
	}

	public List<AssetListingRO> fetchAssetByIdName(int customerId, String customerName) {
		System.out.println("Printing :: "+ customerId);
		List<AssetListingRO> assetList = new ArrayList<AssetListingRO>();
		List<AssetListing> assetListEntity = new ArrayList<AssetListing>();
		if (customerId > 0) {
			 assetListEntity = assetRepo.findByCustomerId(customerId);
			assetListEntity.forEach(f -> {
				AssetListingRO asset = new AssetListingRO();
				asset = mapper.map(f, AssetListingRO.class);
				assetList.add(asset);
			});
		} else if (customerName != "" && customerName != null) {
			assetListEntity = assetRepo.findByAssetName(customerName);
			assetListEntity.forEach(f -> {
				AssetListingRO asset = new AssetListingRO();
				asset = mapper.map(f, AssetListingRO.class);
				assetList.add(asset);
			});
		}
		return assetList;
	}

	public AssetListingRO fetchAssetById(int assetId) {
		return mapper.map(assetRepo.findById(assetId).get(), AssetListingRO.class);
	}

	public List<AssetListingRO> fetchAllAsset() {
		return assetRepo.findAll().stream().map(cr -> mapper.map(cr, AssetListingRO.class))
				.collect(Collectors.toList());
	}

	public AssetListingRO updateRMPaymentTerms(PaymentTermsRO paymentTerms) {

		AssetListing asset = assetRepo.findById(paymentTerms.getAssetId()).get();
		PaymentTerms paymentTerm = mapper.map(paymentTerms, PaymentTerms.class);
		paymentTerm.setUpdatedBy("RM");
		paymentTerm.setUpdatedOn(ZonedDateTime.now(ZoneId.of("UTC")));
		paymentTerm.setDuration(paymentTerm.getRMDuration());
		if (paymentTerms.getRMStatus().equalsIgnoreCase(DEFIConstants.RM_STATUS_GO)) {
			asset.setUpdatedBy("RM");
			asset.setUpdatedOn(ZonedDateTime.now(ZoneId.of("UTC")));
			asset.setPaymentTerms(paymentTerm);
			assetRepo.save(asset);
		} else if (paymentTerms.getRMStatus().equalsIgnoreCase(DEFIConstants.RM_STATUS_COUNTER_OFFER)){
			asset.setUpdatedBy("RM");
			asset.setUpdatedOn(ZonedDateTime.now(ZoneId.of("UTC")));
			asset.setPaymentTerms(paymentTerm);
			assetRepo.save(asset);
		} else if (paymentTerms.getRMStatus().equalsIgnoreCase(DEFIConstants.RM_STATUS_NO_GO)){
			asset.setEligibility("false");
			asset.setPaymentTerms(paymentTerm);
			asset.setUpdatedBy("RM");
			assetRepo.save(asset);
		}

		// Set the notification status
		Notification notification = new Notification();
		notification.setPkCol(asset.getAssetId());
		notification.setTableName("AssetListing");
		notification.setNotificationStatus("true");
		notification.setCustomerId(asset.getCustomerId());
		notification.setComments(
				"ASSET LISTING VALIDATED BY RM");
		notifyRepo.save(notification);

		return mapper.map(asset, AssetListingRO.class);

	}

	public AssetListingRO updateCustomerPaymentTerms(int assetId, String customerStatus) {

		AssetListing asset = assetRepo.findById(assetId).get();

		PaymentTerms paymentTerm = asset.getPaymentTerms();
		paymentTerm.setUpdatedBy("Customer");
		paymentTerm.setUpdatedOn(ZonedDateTime.now(ZoneId.of("UTC")));
		paymentTerm.setCustomerStatus(customerStatus);
		if (customerStatus.equalsIgnoreCase(DEFIConstants.CUSTOMER_STATUS_GO)) {
			asset.setUpdatedBy("AO");
			asset.setUpdatedOn(ZonedDateTime.now(ZoneId.of("UTC")));
			asset.setEligibility("true");
			asset.setPaymentTerms(paymentTerm);
			assetRepo.save(asset);
		} else {
			paymentRepo.save(paymentTerm);
		}

		// Set the notification status
		Notification notification = new Notification();
		notification.setPkCol(asset.getAssetId());
		notification.setTableName("AssetListing");
		notification.setNotificationStatus("true");
		notification.setCustomerId(DEFIConstants.DEFAULT_RM_CUSTOMER_ID);
		if ("GO".equalsIgnoreCase(customerStatus)) {
			notification.setComments("CUSTOMER ACCEPTED RM OFFER FOR ASSET LISTING");
		} else {
			notification.setComments("CUSTOMER REJECTED RM OFFER FOR ASSET LISTING");
		}
		
		notifyRepo.save(notification);

		return mapper.map(asset, AssetListingRO.class);

	}

	public List<AssetListingRO> fetchValidatedAssets() {
		List<AssetListingRO> assetList = new ArrayList<AssetListingRO>();
		List<AssetListing> assetListEntity = new ArrayList<AssetListing>();
		 assetListEntity = assetRepo.findByEligibility("true");
		assetListEntity.forEach(f -> {
			AssetListingRO asset = new AssetListingRO();
			asset = mapper.map(f, AssetListingRO.class);
			assetList.add(asset);
		});
		return assetList;
	}
	
	public List<AssetListingRO> fetchValidationPendingAssets() {
		List<AssetListingRO> assetList = new ArrayList<AssetListingRO>();
		List<AssetListing> assetListEntity = new ArrayList<AssetListing>();
		 assetListEntity = assetRepo.findByEligibility("false");
		assetListEntity.forEach(f -> {
			AssetListingRO asset = new AssetListingRO();
			asset = mapper.map(f, AssetListingRO.class);
			assetList.add(asset);
		});
		return assetList;
	}
}
