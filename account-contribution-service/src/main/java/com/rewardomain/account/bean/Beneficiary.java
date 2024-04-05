package com.rewardomain.account.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Beneficiary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="allocation_percentage")
	private double percentage;
	
	@Column
	private String name;
	
	@Column
	private double savings;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="account_id")
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

	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}
}
