package com.rewardomain.rewardmanager.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Account {

	private Long id;
	
	
	private String owner;
	
	@JsonProperty("account_number")
	private String number;
	
	
	private double benefits;
	
	@JsonIgnore
	private List<Beneficiary> beneficiaries = new ArrayList<>();
	
	@JsonIgnore
	private CreditCard creditCard;
	
	public Account() {}

	public Account(String owner, String number) {
		// TODO Auto-generated constructor stub
		this.owner = owner;
		this.number = number;
	}
	
	public Account(long id, String owner, String number, CreditCard creditCard, double benefits) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.owner = owner;
		this.number = number;
		this.creditCard = creditCard;
		this.benefits = benefits;
	}
	
	public Account(String owner,CreditCard creditCard, String number) {
		// TODO Auto-generated constructor stub
		this.owner = owner;
		this.creditCard = creditCard;
		this.number = number;
	}
	
	

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
		
	}

	public List<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}

	public boolean isValid() {
		double totalPercentage = 0.0;
		for (Beneficiary beneficiary : beneficiaries) {
			totalPercentage += beneficiary.getPercentage();
		}
		return totalPercentage == 100.0 ? true : false;
	}

	public double getBenefits() {
		// TODO Auto-generated method stub
		return benefits;
	}

	public void setBenefits(double benefits) {
		// TODO Auto-generated method stub
		this.benefits = benefits;
		
	}

	public String getNumber() {
		// TODO Auto-generated method stub
		return number;
	}
	
}
