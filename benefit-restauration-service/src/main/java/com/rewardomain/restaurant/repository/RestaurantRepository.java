package com.rewardomain.restaurant.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.restaurant.bean.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByNumber(Long number);
   
    Optional<Restaurant> findById(Long id);
    List<Restaurant> findAll();
    
    @SuppressWarnings("unchecked")
   	Restaurant save(Restaurant restaurant);
}



