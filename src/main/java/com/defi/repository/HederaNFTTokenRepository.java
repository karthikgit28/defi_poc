package com.defi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.defi.entity.HederaNFTToken;

@Repository
public interface HederaNFTTokenRepository extends JpaRepository<HederaNFTToken, Integer>{

	public List<HederaNFTToken> findByCustomerId(int cId);
}
