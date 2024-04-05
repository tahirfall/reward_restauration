package com.rewardomain.account.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String owner;
	
	@JsonProperty("account_number")
	@Column(name="account_number")
	private String number;
	
	@Column(name="total_benefits")
	private double benefits;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private List<Beneficiary> beneficiaries = new ArrayList<>();
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="credit_card_id")
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
