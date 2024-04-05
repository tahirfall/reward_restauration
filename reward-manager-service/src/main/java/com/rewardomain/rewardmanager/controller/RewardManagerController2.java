/*
package com.rewardomain.rewardmanager.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rewardomain.rewardmanager.bean.AccountContributionResponse;
import com.rewardomain.rewardmanager.bean.BenefitRestaurant;
import com.rewardomain.rewardmanager.bean.Confirmation;
import com.rewardomain.rewardmanager.bean.Dining;
import com.rewardomain.rewardmanager.bean.Reward;
import com.rewardomain.rewardmanager.proxy.AccountContributionProxy;
import com.rewardomain.rewardmanager.proxy.BenefitCalculationProxy;
import com.rewardomain.rewardmanager.repository.RewardRepository;


@RestController
public class RewardManagerController2 {
	
	@Autowired
	private RewardRepository repository;
	@Autowired
	private BenefitCalculationProxy proxy;
	
	@Autowired
	private AccountContributionProxy accountContributionProxy;
	
	
	
	@Autowired
	private Environment environment;
	
	
	@GetMapping("/reward-manager/hello")
	public String hello() {
		return "Hello World";
	}
	
	@PostMapping("/reward-manager-old/rewards")
	public ResponseEntity<Confirmation> createRewardold(@RequestBody Dining dining) {
		long merchantNumber = dining.getMerchantNumber();
		double diningAmount = dining.getDiningAmount();
		//Benefit calculation invoked
		BenefitRestaurant benefitRestaurant = proxy.getBenefitRestaurant(merchantNumber, diningAmount);
		System.out.println(benefitRestaurant.getExecutionChain());
		String port = environment.getProperty("local.server.port");
		Reward reward = new Reward (
				dining.getId()+100,
				dining.getId()+1000,
				benefitRestaurant.getAmount(),
				merchantNumber,
				LocalDateTime.parse(dining.getDiningDate(),
						DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))		
				);
			//Save the reward in the
			repository.save(reward);
			
			
			HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(200);

			
			Confirmation confirmation = new Confirmation(reward.getConfirmationNumber(),benefitRestaurant.getExecutionChain() );
			confirmation.setExecutionChain(
					"reward-manager instance - " + port + " == invoked => " + benefitRestaurant.getExecutionChain()
			);	
			return new ResponseEntity<Confirmation>(confirmation, httpStatusCode);
	}
	@PostMapping("/reward-manager/rewards")
	public ResponseEntity<Confirmation> createReward(@RequestBody Dining dining) {
		long merchantNumber = dining.getMerchantNumber();
		double diningAmount = dining.getDiningAmount();
		
		BenefitRestaurant benefitRestaurant = proxy.getBenefitRestaurant(merchantNumber, diningAmount);
		System.out.println("amount: "+ benefitRestaurant.getAmount());
		
		
		String port = environment.getProperty("local.server.port");
		Reward reward = new Reward (
				dining.getId()+100,
				dining.getId()+1000,
				benefitRestaurant.getAmount(),
				merchantNumber,
				LocalDateTime.parse(dining.getDiningDate(),
						DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))		
				);
			//Save the reward in the
			repository.save(reward);
					
			HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(200);
	
			
			Confirmation confirmation = new Confirmation(reward.getConfirmationNumber(),benefitRestaurant.getExecutionChain() );
			confirmation.setExecutionChain(
					"reward-manager instance - " + port + " == invoked => " + benefitRestaurant.getExecutionChain()
			);	
	
			return new ResponseEntity<Confirmation>(confirmation, httpStatusCode);
	}
	//get all rewards
	@GetMapping("/reward-manager/rewards")
	public ResponseEntity<Object> getRewards() {
		return ResponseEntity.status(200).body(repository.findAll());
	}
	
	@GetMapping("/reward-manager/rewards/{reward_confirmation_number}")
	public Reward getRewardByMechantNumber(@PathVariable("reward_confirmation_number") long confirmationNumber) {
		return repository.findByConfirmationNumber(confirmationNumber);	
	}
	
	@PostMapping("/reward-manager/rewards/{credit_card_number}/{reward_confirmation_number}")
    public AccountContributionResponse distribution(@PathVariable("reward_confirmation_number") long reward_confirmation_number,@PathVariable("credit_card_number") String credit_card_number) {

        Reward reward = repository.findByConfirmationNumber(reward_confirmation_number);

        String port = environment.getProperty("local.server.port");
		
        if (reward == null) {
            throw new RuntimeException("Recompense non trouvée !");
        }
        
        double amount = reward.getAmount();
        
        AccountContributionResponse accountContributionResponse = accountContributionProxy.distributeReward(credit_card_number,amount);
        accountContributionResponse.setExecutionChain("reward-service instance: " + port + "==invoked => " + accountContributionResponse.getExecutionChain());
       
        return 	accountContributionResponse;
        
       
       
    }

	
}

*/