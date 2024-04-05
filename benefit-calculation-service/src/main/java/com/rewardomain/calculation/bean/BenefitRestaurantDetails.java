package com.rewardomain.calculation.bean;


public class BenefitRestaurantDetails {
	private int id;
	private int number;
	private String name;
	private double percentage;
	private String availability;
	
	
    private String executionChain;
	
	public BenefitRestaurantDetails() {}
	public BenefitRestaurantDetails(int id, int number, String name, double percentage, String availability) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.percentage = percentage;
		this.availability = availability;
	}
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPercentage() {
        return percentage;
    }
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getAvailability() {
        return availability;
    }
    public void setAvailability(String availability) {
        this.availability = availability;
    }
    
    public String getExecutionChain() {
		return executionChain;
	}
	public void setExecutionChain(String executionChain) {
		this.executionChain = executionChain;
	}
	
}
