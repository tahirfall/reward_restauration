package com.rewardomain.rewardmanager.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Beneficiary {
	
	private Long id;
	
	private double percentage;
	
	private String name;
	
	private double savings;
	
	@JsonIgnore
	private Account account;
	
	public Beneficiary() {}

	public Beneficiary(double percentage, String name) {
		this.percentage = percentage;
		this.name = name;
	}

	public double getSavings() {
		return savings;
	}
	public void setSavings(double savings) {
		this.savings = savings;
	}
	
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public void setAccount(Account account) {
		this.account = account;
		
	}
}
