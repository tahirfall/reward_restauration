package com.rewardomain.rewardmanager.bean;

public class CreditCard {
	
	private Long id;
	
	private String number;
	
	private Account account;
	
	public CreditCard() {}

	public CreditCard(String number) {
		this.number = number;
	}
	
	
}
