package com.rewardomain.restaurant.response;

public class RestaurantResponse {

    private int status_code;
    private String message;
    private String chain;

    public RestaurantResponse() {
    }

    public RestaurantResponse(int status_code, String message) {
        this.status_code = status_code;
        this.message = message;
    }
    
    public RestaurantResponse(int status_code, String message, String chain) {
        this.status_code = status_code;
        this.message = message;
        this.chain = chain;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }
}
