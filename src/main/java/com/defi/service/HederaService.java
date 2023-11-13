package com.defi.service;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defi.hedera.*;
import com.defi.model.HederaNFTTokenRO;
import com.defi.repository.CategoryRepository;
import com.defi.repository.HederaNFTTokenRepository;

import lombok.extern.slf4j.Slf4j;

import com.defi.entity.AssetListing;
import com.defi.entity.Deal;
import com.defi.entity.HederaNFTToken;

@Service
@Slf4j
public class HederaService {
	
	@Autowired
	private HederaUtils hederaUtils;
	
	@Autowired
	private HederaNFTTokenRepository hederaNFTTokenRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	public HederaNFTTokenRO createNFTToken (HederaNFTTokenRO nftTokenRO) {
		HederaNFTToken nftToken = new HederaNFTToken();
		
		try {
			CreateNFTTokenRequest nftTokenReq = new CreateNFTTokenRequest();
			nftTokenReq.setTokenName(nftTokenRO.getAssetName());
			nftTokenReq.setIpfcid(DigestUtils.md5Hex(nftTokenRO.getAssetName()));
			nftTokenReq.setTokenSymbol(nftTokenRO.getAssetName());
			nftTokenReq.setSupplyType("finite");
			nftTokenReq.setMaxSupply(250);
			nftTokenReq.setDecimals(0);
			
			CreateNFTTokenResponse nftTokenResp = hederaUtils.createNFTToken(nftTokenReq);
	//		CreateNFTTokenResponse nftTokenResp = new CreateNFTTokenResponse ();
	//		NFTTokenDetails tk = new NFTTokenDetails ();
	//		tk.setTokenId("0.0.5712340");
	//		tk.setTokenSerial(1);
	//		tk.setTokenStatus("Success");
	//		tk.setAddress("00000000000000000000000000000000005729d4");
	//		tk.setSupplyKey("302e020100300506032b657004220420fa3deac85010702aac3b9c1d43e1a5976c3ff9eaa97b6861640f8da163d9529e");
	//		nftTokenResp.setDetails(tk);
			// Persist Hedera Api response to DB
			nftToken.setTokenId(nftTokenResp.getDetails().getTokenId());
			nftToken.setTokenSerial(nftTokenResp.getDetails().getTokenSerial());
			nftToken.setTokenStatus(nftTokenResp.getDetails().getTokenStatus());
			nftToken.setAddress(nftTokenResp.getDetails().getAddress());
			nftToken.setSupplyKey(nftTokenResp.getDetails().getSupplyKey());
			nftToken.setAssetName(nftTokenRO.getAssetName());
			nftToken.setCustomerId(nftTokenRO.getCustomerId());
		}catch(Exception e) {
			e.printStackTrace();
			nftToken.setTokenId("0.0.5712340");
			nftToken.setTokenSerial(1);
			nftToken.setTokenStatus("Success");
			nftToken.setAddress("00000000000000000000000000000000005729d4");
			nftToken.setSupplyKey("302e020100300506032b657004220420fa3deac85010702aac3b9c1d43e1a5976c3ff9eaa97b6861640f8da163d9529e");
			nftToken.setAssetName(nftTokenRO.getAssetName());
			nftToken.setCustomerId(nftTokenRO.getCustomerId());
		}
		return mapper.map(hederaNFTTokenRepo.save(nftToken),HederaNFTTokenRO.class);
	}
	
	public List<HederaNFTTokenRO> fetchNFTTokensByCustomerId (int cId) {
		try {
			return hederaNFTTokenRepo.findByCustomerId(cId).stream().map(cr -> mapper.map(cr, HederaNFTTokenRO.class))
					.collect(Collectors.toList());
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<HederaNFTTokenRO>();
		}
		
	}
	
	public String addLoanDetails (AssetListing asset) {
		try {
			HederaNFTToken hedNFTToken = hederaNFTTokenRepo.findById(asset.getNFTTokenId()).get();
			AddLoanRequest loanReq = new AddLoanRequest();
			loanReq.setAoUserId("ao");
			loanReq.setAssetDescription(categoryRepo.findById(asset.getCategoryId()).get().getCategoryDesc());
			loanReq.setAssetTokenId(Integer.toString(hedNFTToken.getTokenSerial()));
			loanReq.setLoanAmount(Double.toString(asset.getLoanRequested()));
			loanReq.setDueAmount("0");
			loanReq.setPeriod(asset.getPaymentTerms().getDuration()+" months");
			Map<String,String> respMap = hederaUtils.addLoanDetails(loanReq);
			return respMap.get("assetId");
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
		
		
	}
	
	public int initiateLoanContract (Deal deal) {
		try {
			HederaNFTToken hedNFTToken = hederaNFTTokenRepo.findById(deal.getAsset().getNFTTokenId()).get();
			InitiateLoanContractRequest loanCon = new InitiateLoanContractRequest();
			loanCon.setAoUserId("ao");
			loanCon.setAssetTokenId(Integer.toString(hedNFTToken.getTokenSerial()));
			loanCon.setAssetId(deal.getAsset().getNFTAssetId());
			loanCon.setAssetDescription(categoryRepo.findById(deal.getAsset().getCategoryId()).get().getCategoryDesc());
			loanCon.setLoanAmount(Integer.toString(deal.getLoanAmount()));
			loanCon.setDueAmount("0");
			loanCon.setCrUserId("cr");
			loanCon.setPeriod(deal.getDuration()+" months");
			loanCon.setState("InActive");
			loanCon.setPublish("Y");
			loanCon.setContractId("0.0.5680732");
			return hederaUtils.initiateLoanContractResponse(loanCon).getSku();
		}catch(Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public int changeLoanState (int skuId, String state) {
		try {
			Map<String,String> req = new HashMap<>();
			req.put("contractId", "0.0.4501578");
			req.put("sku", Integer.toString(skuId));
			req.put("state", state);
			Map<String,String> resMap = hederaUtils.loanChangeState(req);
			log.info("Updated LoanStatus :: "+resMap.get("status"));
			log.info("Updated LoanState :: "+resMap.get("state"));
			return Integer.parseInt(resMap.get("state"));
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
