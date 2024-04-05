package com.rewardomain.account.bean;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Distribution {
	
	public Distribution() {}
	
	public void distribute(List<Beneficiary> beneficiaries, double reward) {
		for(Beneficiary beneficiary : beneficiaries) {
			beneficiary.setSavings(beneficiary.getSavings() + reward * (beneficiary.getPercentage() / 100.0));
		}
	}
}
