package com.rewardomain.rewardmanager.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dining {
	private long id;
	
	@JsonProperty("credit_card_number")
	private String creditCardNumber;
	
	@JsonProperty("merchant_number")
	private long merchantNumber;
	
	@JsonProperty("dining_amount")
	private double diningAmount;
	
	@JsonProperty("dining_date")
	private String diningDate;
	
	public Dining() {}
	
	public long getId() {
		return id;
	}
	
	public String getCreditcardNumber() {
		return creditCardNumber;
	}
	
	public long getMerchantnumber() {
		return merchantNumber;
	}
	
	public double getDiningAmount() {
		return diningAmount;
	}
	
	public String getDiningDate() {
		return diningDate;
	}

	public long getMerchantNumber() {
		// TODO Auto-generated method stub
		return merchantNumber;
	}
}
