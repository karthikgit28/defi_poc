package com.defi.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.defi.entity.Deal;

@Repository
public interface DealRepository extends JpaRepository<Deal, Integer>{

	List<Deal> findByDealStatusAndExpiryDateLessThan(String dealStatus,ZonedDateTime plusMonths);
	List<Deal> findByDealStatus (String dealStatus);
	List<Deal> findByAssetId (int assetId);
	List<Deal> findByCashRichOfferId (int croId);
}
