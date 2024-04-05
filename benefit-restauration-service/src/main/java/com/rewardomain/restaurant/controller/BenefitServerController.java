package com.rewardomain.restaurant.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import com.rewardomain.restaurant.bean.BenefitServer;
import com.rewardomain.restaurant.bean.Restaurant;
import com.rewardomain.restaurant.bean.RestaurantError;
import com.rewardomain.restaurant.bean.RestaurantRequest;
//import com.rewardomain.restaurant.configuration.Configuration;
import com.rewardomain.restaurant.repository.RestaurantRepository;
import com.rewardomain.restaurant.response.RestaurantResponse;

import io.github.resilience4j.retry.annotation.Retry;

@RestController 
public class BenefitServerController{
    //@Autowired 
    //private Configuration configuration;
    @Autowired
    private RestaurantRepository repository;
    
    /*
    @GetMapping("/benefitserver")
    public BenefitServer getBenefitServer(){
        return new BenefitServer(configuration.getType(), configuration.getName());
    }
    */
    
    @Autowired
    private Environment environment;
    
    private Logger logger = LoggerFactory.getLogger(BenefitServerController.class);
    
    public ResponseEntity<Restaurant> defaultResponse(RuntimeException e) {
    	HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
    	return new ResponseEntity<>(new RestaurantError(404L, e.getMessage()), httpStatusCode);
    }
    
    @Retry(name= "benefit-restaurant", fallbackMethod ="defaultResponse")
    @GetMapping("/benefit-restaurant/merchants/{merchant_number}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable long merchant_number) {
    	logger.info("Benefit Restaurant Service call service");
    	
        Restaurant restaurant = repository.findByNumber(merchant_number);

        String port = environment.getProperty("local.server.port");
        
        HttpStatusCode httpStatusCode;
        if (restaurant == null) {
        	throw new RuntimeException("Restaurant not found for the merchant number : " + merchant_number + ".");
            //restaurant = new Restaurant();
            //httpStatusCode = HttpStatusCode.valueOf(404);
        }

        restaurant.setExecutionChain("restaurant service instance:" + port);
        httpStatusCode = HttpStatusCode.valueOf(200);
	
        return new ResponseEntity<>(restaurant, httpStatusCode);
   
    }
     
    @Retry(name= "benefit-restaurant", fallbackMethod ="defaultResponse")
    @PostMapping("/benefit-restaurant/merchants")
    public ResponseEntity<RestaurantResponse> addRestaurant(@RequestBody Restaurant restaurant) {
    	logger.info("Benefit Restaurant Service call service");
    	
    	 String port = environment.getProperty("local.server.port");
    	
    	if(restaurant == null)
    		throw new RuntimeException("Restaurant is null. Not added!");
    	else {
    		restaurant.setExecutionChain("restaurant service instance:" + port);
    		repository.save(restaurant);
    		
    		RestaurantResponse response = new RestaurantResponse(200, "Benefit restaurant added.", "restaurant-service instance:" + port);

    		return ResponseEntity.ok(response);
    	}
        
    }
    
    @Retry(name= "benefit-restaurant", fallbackMethod ="defaultResponse")
    @GetMapping("/benefit-restaurant/merchants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
    	logger.info("Benefit Restaurant Service call service");
    	
    	List<Restaurant> restaurants = repository.findAll();
    	
    	if(restaurants.isEmpty())
    		throw new RuntimeException("List of restaurant is empty!");
    	else	
    		return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatusCode.valueOf(200));
    }

    
    @Retry(name = "benefit-restaurant", fallbackMethod = "defaultResponse")
    @PutMapping("/benefit-restaurant/merchants/{merchant_number}/{availability}")
    public ResponseEntity<RestaurantResponse> updateAvailability(
            @PathVariable long merchant_number,
            @PathVariable String availability) {
    	   logger.info("Benefit Restaurant Service call received.");
        Restaurant restaurant = repository.findByNumber(merchant_number);

        if (restaurant == null) {
        	throw new RuntimeException("Restaurant not found for the merchant number: " + merchant_number + ".");
            /*String port = environment.getProperty("local.server.port");
            String executionChain = "restaurant-service instance:" + port;
            RestaurantResponse response = new RestaurantResponse(404, "Restaurant not found.", executionChain);
            return new ResponseEntity<>(response, HttpStatus.CREATED);*/
        	
        }

        restaurant.setAvailability(availability);
        repository.save(restaurant);

        String port = environment.getProperty("local.server.port");
        RestaurantResponse response = new RestaurantResponse(200, "Benefit availability updated.", "restaurant-service instance:" + port);

        return ResponseEntity.ok(response);
    }
    
    /*
    @Retry(name= "benefit-restaurant", fallbackMethod ="defaultResponse")
    @PutMapping("/benefit-restaurant/merchants/{merchant_number}")
    public ResponseEntity<RestaurantResponse> updateAvailability(@RequestBody RestaurantRequest request, @PathVariable long merchant_number ) {
    	logger.info("Benefit Restaurant Service call service");
    	
    	Restaurant restaurant = repository.findByNumber(merchant_number);

        if (restaurant == null) {
        	throw new RuntimeException("Restaurant not found for the merchant number : " + merchant_number + ".");
        }

        restaurant.setAvailability(request.getAvailability());
        repository.save(restaurant);

        return new ResponseEntity<RestaurantResponse>
    	(new RestaurantResponse(200, "Benefit Restaurant updated."), HttpStatusCode.valueOf(200));
    }*/
    


}



