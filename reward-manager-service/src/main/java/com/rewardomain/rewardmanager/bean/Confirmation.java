package com.rewardomain.rewardmanager.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Confirmation {
	@JsonProperty("reward_confirmation_number")
	private long rewardConfirmation;
	
	private String executionChain;
	
	public Confirmation() {}
	public Confirmation(long rewardConfirmation) {
		this.setRewardConfimation(rewardConfirmation);
	}
	public Confirmation(long rewardConfirmation, String chain) {
		this.setRewardConfimation(rewardConfirmation);
		this.setExecutionChain(chain);
	}
	
	public long getRewardConfirmation() {
		return rewardConfirmation;
	}
	
	public void setRewardConfimation(long rewardConfirmation) {
		this.rewardConfirmation = rewardConfirmation;
	}
	
	public String getExecutionChain() {
		return executionChain;
	}
	public void setExecutionChain(String executionChain) {
		// TODO Auto-generated method stub
		this.executionChain = executionChain;
		
	}
}

