package com.rewardomain.account.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class CreditCard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="credit_card_number")
	private String number;
	
	@OneToOne(mappedBy = "creditCard")
	private Account account;
	
	public CreditCard() {}

	public CreditCard(String number) {
		this.number = number;
	}
	
	
}
