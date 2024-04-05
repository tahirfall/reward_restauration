package com.rewardomain.rewardmanager.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountContributionResponse {
	@JsonProperty("status_code")
	private int code;
	
	private String message;

	public AccountContributionResponse(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public AccountContributionResponse() {
		super();
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */

	public AccountContributionResponse(int code, String message,String executionChain) {
		super();
		this.code = code;
		this.message = message;
		this.executionChain=executionChain;
	}
	
	public AccountContributionResponse(String executionChain) {
		this.executionChain=executionChain;
	}

	@JsonProperty("execution_chain")
	private String executionChain;
	public String getExecutionChain() {
		return executionChain;
	}
	public void setExecutionChain(String executionChain) {
		this.executionChain = executionChain;
	}	
}