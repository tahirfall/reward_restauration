package com.rewardomain.rewardmanager.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Transient;

public class BenefitRestaurant {
	@JsonProperty("benefit_amount")
	private double amount;
	
	@Transient
    private String executionChain;
	
	public BenefitRestaurant() {}
	public BenefitRestaurant(double amount, String executionChain) {
		this.setAmount(amount);
		this.executionChain = executionChain;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getExecutionChain() {
		return executionChain;
	}
	public void setExecutionChain(String executionChain) {
		this.executionChain = executionChain;
	}
	
	
}


