package com.defi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.defi.entity.PaymentTerms;

@Repository
public interface PaymentTermsRepository extends JpaRepository<PaymentTerms, Integer>{

}
