package com.rewardomain.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudGatewayConfiguration {
	
	@Bean
	RouteLocator gatewayRouter (RouteLocatorBuilder builder) {		
		return builder.routes()
			.route(p -> p
					.path("/get")
					.filters(f -> f
									.addRequestHeader("myHeaderParam", "headerParamValue")
									.addRequestParameter("myParam", "paramValue"))
					.uri("http://httpbin.org:80"))
			.route(p -> p
					.path("/benefit-restaurant/**")
					.uri("lb://benefit-restaurant-service"))
			.route(p -> p
					.path("/benefit-calculation/**")
					.uri("lb://benefit-calculation-service"))
			.route(p -> p
					.path("/reward-manager/**")
					.uri("lb://reward-manager-service"))
			.route(p -> p
					.path("/account-contribution/**")
					.uri("lb://account-contribution-service"))
			.route(p -> p
					.path("/currency-exchange/**")
					.uri("lb://currency-exchange-service"))
			.build();
	}
}
