package com.rewardomain.account.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "owner", "number", "benefits", "beneficiaries", "creditCard"})
public class AccountError extends Account{
	@JsonProperty("status_code")
	private Long statuscode;
	
	@JsonProperty("error_message")
	private String message;
	
	public AccountError() {}
	public AccountError(long statuscode, String message) {
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
