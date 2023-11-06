package com.defi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.defi.entity.AssetListing;

@Repository
public interface AssetListingRepository extends JpaRepository<AssetListing, Integer>{
	
	List<AssetListing> findByCustomerId(int customerId);
	List<AssetListing> findByAssetName(String assetName);
	List<AssetListing> findByEligibility(String eligibility);

}
