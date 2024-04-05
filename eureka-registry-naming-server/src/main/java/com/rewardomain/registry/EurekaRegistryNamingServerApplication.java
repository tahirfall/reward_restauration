package com.rewardomain.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaRegistryNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaRegistryNamingServerApplication.class, args);
	}

}
