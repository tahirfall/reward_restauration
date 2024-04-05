package com.rewardomain.rewardmanager.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rewardomain.rewardmanager.bean.Account;
import com.rewardomain.rewardmanager.bean.AccountContributionResponse;
import com.rewardomain.rewardmanager.bean.BenefitRestaurant;
import com.rewardomain.rewardmanager.bean.Confirmation;
import com.rewardomain.rewardmanager.bean.Dining;
import com.rewardomain.rewardmanager.bean.Reward;
import com.rewardomain.rewardmanager.bean.RewardError;
import com.rewardomain.rewardmanager.proxy.AccountContributionProxy;
import com.rewardomain.rewardmanager.proxy.BenefitCalculationProxy;
import com.rewardomain.rewardmanager.repository.RewardRepository;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class RewardManagerController {
	
	@Autowired
	private BenefitCalculationProxy bcproxy;
	
	@Autowired
	private RewardRepository repository;
	
	@Autowired
    private Environment environment;
	
	 @Autowired
	 private AccountContributionProxy acproxy;
	 
	 private Logger logger = LoggerFactory.getLogger(RewardManagerController.class);
	 
	 
	 public ResponseEntity<Reward> defaultResponse(RuntimeException e) {
	    	HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
	    	return new ResponseEntity<>(new RewardError(404L, e.getMessage()), httpStatusCode);
	    }
	 
	 @PostMapping("/reward-manager/rewards")
	 public ResponseEntity<Confirmation> createReward(@RequestBody Dining dining) {
	     long merchantNumber = dining.getMerchantNumber();
	     double diningAmount = dining.getDiningAmount();
	     
	     BenefitRestaurant benefitRestaurant = bcproxy.getBenefitRestaurant(merchantNumber, diningAmount);
	     
	     double rewardAmount = benefitRestaurant.getAmount();
	     
	     String port = environment.getProperty("local.server.port");

	     String executionChain = "reward_manager:" + port;

	     if (benefitRestaurant.getExecutionChain() != null && !benefitRestaurant.getExecutionChain().isEmpty()) {
	         executionChain += " => " + benefitRestaurant.getExecutionChain();
	     }

	     Reward reward = new Reward(
	             dining.getId() + 100,
	             dining.getId() + 1000,
	             rewardAmount, 
	             merchantNumber,
	             LocalDateTime.parse(dining.getDiningDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
	     );

	     
	     repository.save(reward);

	     Confirmation confirmation = new Confirmation(reward.getConfirmationNumber(), executionChain);

	     return ResponseEntity.ok(confirmation);
	 }

	 
	 /*
	 //@Retry(name = "reward-manager", fallbackMethod = "defaultResponse")
		@GetMapping("/reward-manager/distribute/{credit_card_number}/reward/{reward_confirmation_number}")
		public ResponseEntity<AccountContributionResponse> distribute(@PathVariable("credit_card_number") String cardNumber, @PathVariable("reward_confirmation_number") long rewardNumber) {
			Account account;
			try {
				account = acproxy.getAccountByCreditCardNumber(cardNumber);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("Credit card invalid.");
			}
			Reward reward = repository.findByConfirmationNumber(rewardNumber);
			if(reward == null) {
				throw new RuntimeException("Reward not found.");
			}
			
			AccountContributionResponse response = acproxy.distributeReward(cardNumber, reward.getAmount());
			return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
		}	    
	    */
	 @Retry(name= "reward-manager", fallbackMethod ="defaultResponse")
	 @PostMapping("/reward-manager/rewards/{credit_card_number}/{reward_confirmation_number}")
	    public ResponseEntity<Confirmation> distributeRewardWithoutCreatingReward(@PathVariable String credit_card_number, @PathVariable long reward_confirmation_number) {
		 	
		 	logger.info("Reward Manager Service call service");
		 
	        Reward reward = repository.findByConfirmationNumber(reward_confirmation_number);

	      
	        if (reward == null) {
	        	throw new RuntimeException("Reward not found for the reward confirmation number : " + reward_confirmation_number + ".");
	        }

	       
	        String port = environment.getProperty("local.server.port");

	      
	        ResponseEntity<AccountContributionResponse> accountContributionResponse =
	                acproxy.distributeReward(credit_card_number, reward.getAmount());

	        if (accountContributionResponse.getStatusCode() == HttpStatus.OK) {
	          
	            AccountContributionResponse contributionResponse = accountContributionResponse.getBody();

	           
	            String accountContributionExecutionChain = contributionResponse.getExecutionChain();

	           
	            String updatedExecutionChain = "reward_manager_instance:" + port + " invoked =>" + accountContributionExecutionChain;

	            Confirmation confirmation = new Confirmation(reward_confirmation_number);
	            confirmation.setExecutionChain(updatedExecutionChain);

	            return ResponseEntity.ok(confirmation);
	        } else {
	          
	            return ResponseEntity.status(accountContributionResponse.getStatusCode()).build();
	        }
	    }
		
		
	    
	    @Retry(name= "reward-manager", fallbackMethod ="defaultResponse")
	    @GetMapping("/reward-manager/rewards/{reward_confirmation_number}")
	    public ResponseEntity<Reward> getReward(@PathVariable long reward_confirmation_number) {
	    	logger.info("Reward Manager Service call service");
	    	
	        Reward reward = repository.findByConfirmationNumber(reward_confirmation_number);

	        if (reward == null) {
	            throw new RuntimeException("Reward not found for the reward confirmation number : " + reward_confirmation_number + ".");
	        }

	        return new ResponseEntity<>(reward, HttpStatusCode.valueOf(200));
	    }
}





