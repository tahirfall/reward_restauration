package com.rewardomain.calculation.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "number", "name", "percentage", "availability", "executionChain"})
public class BenefitRestaurantDetailsError extends BenefitRestaurantDetails{
	@JsonProperty("status_code")
	private Long statuscode;
	
	@JsonProperty("error_message")
	private String message;
	
	public BenefitRestaurantDetailsError() {}
	public BenefitRestaurantDetailsError(long statuscode, String message) {
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
