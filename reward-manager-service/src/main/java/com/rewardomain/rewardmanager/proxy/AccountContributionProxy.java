package com.rewardomain.rewardmanager.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.rewardomain.rewardmanager.bean.AccountContributionResponse;
import com.rewardomain.rewardmanager.bean.Account;

@FeignClient(name="account-contribution-service")
public interface AccountContributionProxy {
    
	@PutMapping("/account-contribution/accounts/{credit_card_number}/reward/{reward}") 
    ResponseEntity<AccountContributionResponse> distributeReward(
    			@PathVariable("credit_card_number") String creditCardNumber, 
    			@PathVariable("reward") double rewardAmount);

    @GetMapping("/account-contribution/accounts/creditCard/{credit_card_number}")
    Account getAccountByCreditCardNumber(@PathVariable("credit_card_number") String creditCardNumber);
	
    //@GetMapping("/account-contribution/accounts/{account_number}")
    //Account getAccountByNumber(@PathVariable String accountNumber);

}