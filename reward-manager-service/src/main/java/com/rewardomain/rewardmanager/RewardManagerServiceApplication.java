package com.rewardomain.rewardmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RewardManagerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardManagerServiceApplication.class, args);
	}

}
