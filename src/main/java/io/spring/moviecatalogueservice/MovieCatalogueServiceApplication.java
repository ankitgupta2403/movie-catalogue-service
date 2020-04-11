package io.spring.moviecatalogueservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class MovieCatalogueServiceApplication {

	@Bean
	@LoadBalanced

	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

/*
@Bean
	public WebClient.Builder getWebClientBuilder()
	{
		return WebClient.builder();
	}
*/

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogueServiceApplication.class, args);
	}

}
