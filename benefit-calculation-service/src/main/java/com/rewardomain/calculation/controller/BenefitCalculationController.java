package com.rewardomain.calculation.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rewardomain.calculation.bean.BenefitRestaurant;
import com.rewardomain.calculation.bean.BenefitRestaurantDetails;
import com.rewardomain.calculation.bean.BenefitRestaurantDetailsError;
import com.rewardomain.calculation.proxy.BenefitRestaurantProxy;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class BenefitCalculationController {
	@Autowired
	private BenefitRestaurantProxy proxy;
	   @Autowired
	    private Environment environment;
	    	
	   private Logger logger = LoggerFactory.getLogger(BenefitCalculationController.class);
		
		public ResponseEntity<BenefitRestaurantDetails> defaultResponse(RuntimeException e) {
	    	HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
	    	return new ResponseEntity<>(new BenefitRestaurantDetailsError(404L, e.getMessage()), httpStatusCode);
	    }

		@Retry(name= "benefit-calculation", fallbackMethod ="defaultResponse")
	   @GetMapping("/benefit-calculation/{merchant_number}/{dining_amount}")
	   public ResponseEntity<BenefitRestaurant> getBenefitAmountFeign(@PathVariable("merchant_number") long number, @PathVariable("dining_amount") double amount) {
	       
	    	logger.info("Account Contribution Service call service");
	    	
	    	BenefitRestaurantDetails benefitRestaurantDetails;
		   try {
			   benefitRestaurantDetails = proxy.getBenefitRestaurantDetails(number); 
		   } catch(Exception e) {
			   throw new RuntimeException("Benefit Restaurant not found for the benefit restaurant number : " + number + ".");
		   }
		   
	       String port = environment.getProperty("local.server.port");
	       benefitRestaurantDetails.setExecutionChain("calculation-service instance:" + port + " == invoked => " + benefitRestaurantDetails.getExecutionChain());

    	   BenefitRestaurant benefitRestaurant = new BenefitRestaurant(amount, benefitRestaurantDetails.getPercentage());
    	   benefitRestaurant.setExecutionChain(benefitRestaurantDetails.getExecutionChain()); 
	       
	       return ResponseEntity.ok(benefitRestaurant);
	   }       
	   
	   
	   
	    /*@GetMapping("/benefit-calculation-rest-template/{merchant_number}/{dining_amount}")
	    public BenefitRestaurant getBenefitAmount(@PathVariable("merchant_number") long number, @PathVariable("dining_amount") double amount) {

	        HashMap<String, String> uriVariables = new HashMap<>();
	        uriVariables.put("merchant_number", String.valueOf(number));

	        ResponseEntity<BenefitRestaurantDetails> responseEntity = new RestTemplate().getForEntity("http://localhost:9190/benefit-restaurant/merchants/{merchant_number}",
	                BenefitRestaurantDetails.class, uriVariables);

	        BenefitRestaurantDetails benefitCalculation = responseEntity.getBody();

	        return new BenefitRestaurant(amount, benefitCalculation.getPercentage());
	    }
	    
	    @GetMapping("/benefit-calculation/{merchant_number}/{dining_amount}")
	    public BenefitRestaurant getBenefitAmountFeign(
	            @PathVariable("merchant_number") long number,
	            @PathVariable("dining_amount") double amount) {

	        BenefitRestaurantDetails benefitRestaurantDetails = proxy.getBenefitRestaurantDetails(number);
	        return new BenefitRestaurant(amount, benefitRestaurantDetails.getPercentage());
	    }*/

}