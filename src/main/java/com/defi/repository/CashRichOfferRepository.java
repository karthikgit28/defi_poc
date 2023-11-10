package com.defi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.defi.entity.CashRichOffer;

@Repository
public interface CashRichOfferRepository extends JpaRepository<CashRichOffer, Integer>{
	
	List<CashRichOffer> findByCustomerId(int cId);
	List<CashRichOffer> findByEligibility(String eligibility);
	
}
