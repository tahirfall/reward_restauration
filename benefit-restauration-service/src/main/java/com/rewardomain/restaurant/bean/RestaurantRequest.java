package com.rewardomain.restaurant.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestaurantRequest {
	
	private String name;
	
	@JsonProperty("merchant_number")
	private String number;
	
	@JsonProperty("benefit_availability_policy")
	private String availability;
	
	@JsonProperty("benefit_percentage")
	private double percentage;

	
	public RestaurantRequest() {}

	public String getName() {
		return name;
	}
	
	public String getNumber() {
		return number;
	}

	public String getAvailability() {
		return availability;
	}

	public double getPercentage() {
		return percentage;
	}
}
