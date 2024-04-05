package com.rewardomain.account.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountContributionRequest {
	
	private String name;
	
	@JsonProperty("credit_card_number")
	private String ccnumber;
	
	@JsonProperty("account_number")
	private String anumber;
	
	@JsonProperty("allocation_percentage")
	private double percentage;

	
	public AccountContributionRequest() {}

	public String getName() {
		return name;
	}
	
	public String getAnumber() {
		return anumber;
	}

	public String getCcnumber() {
		return ccnumber;
	}

	public double getPercentage() {
		return percentage;
	}
}
