package com.rewardomain.rewardmanager.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "confirmationNumber", "amount", "merchantNumber", "rewardDate"})
public class RewardError extends Reward{
	@JsonProperty("status_code")
	private Long statuscode;
	
	@JsonProperty("error_message")
	private String message;
	
	public RewardError() {}
	public RewardError(long statuscode, String message) {
		this.statuscode = statuscode;
		this.message = message;
	}
	
	
	public long getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(long statuscode) {
		this.statuscode = statuscode;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}