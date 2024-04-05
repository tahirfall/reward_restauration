package com.rewardomain.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.account.bean.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	public Account findByNumber(String number);
	Optional<Account> findByCreditCard_Number(String cardNumber);
	
	public List<Account> findAll();
	
}
