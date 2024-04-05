package com.rewardomain.calculation.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rewardomain.calculation.bean.BenefitRestaurantDetails;


//@FeignClient(name="benefit-restaurant", url="localhost:8100")
@FeignClient(name="benefit-restaurant-service")
public interface BenefitRestaurantProxy {
	
	@GetMapping("benefit-restaurant/merchants/{merchant_number}")
	public BenefitRestaurantDetails getBenefitRestaurantDetails (@PathVariable("merchant_number") long number);
}
