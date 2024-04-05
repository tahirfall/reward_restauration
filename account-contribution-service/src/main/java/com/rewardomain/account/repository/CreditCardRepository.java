package com.rewardomain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.account.bean.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {}
