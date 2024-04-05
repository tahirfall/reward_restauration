package com.rewardomain.rewardmanager.bean;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Reward {
	@Id
	private long id;
	
	@Column(name="confirmation_number")
	private long confirmationNumber;
	
	@JsonProperty("amount")
	private double amount;
	
	@Column(name="merchant_number")
	private long merchantNumber;
	
	@Column(name="reward_date")
	private LocalDateTime rewardDate;
	
	
	
	public Reward() {}
	public Reward(long id, long confirmationNumber, double amount, long merchantNumber, LocalDateTime rewardDate) {
		this.id = id;
		this.confirmationNumber = confirmationNumber;
		this.amount = amount;
		this.merchantNumber = merchantNumber;
		this.rewardDate = rewardDate;
	}
	
	public long getConfirmationNumber() {
		return confirmationNumber;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public long getMerchantNumber() {
		// TODO Auto-generated method stub
		return merchantNumber;
	}
	
	
	
	
}
