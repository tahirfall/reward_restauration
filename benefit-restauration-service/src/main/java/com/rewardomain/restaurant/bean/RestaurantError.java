package com.rewardomain.restaurant.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "number", "name", "percentage", "availability", "execution_chain"})
public class RestaurantError extends Restaurant{
	@JsonProperty("status_code")
	private Long statuscode;
	
	@JsonProperty("error_message")
	private String message;
	
	public RestaurantError() {}
	public RestaurantError(long statuscode, String message) {
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
