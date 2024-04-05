package com.rewardomain.calculation.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BenefitRestaurant {
	@JsonProperty("benefit_amount")
	private double amount;
	
	private String executionChain;
	
	public BenefitRestaurant() {}
	public BenefitRestaurant(double diningAmount, double benefitPercentage) {
		this.setAmount(diningAmount * benefitPercentage * .01);
	}
	public BenefitRestaurant(double diningAmount, double benefitPercentage, String chain) {
		this.setAmount(diningAmount * benefitPercentage * .01);
		this.executionChain = chain;
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
		// TODO Auto-generated method stub
		this.executionChain = executionChain;
		
	}
	
}				


