package com.rewardomain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.account.bean.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {}
