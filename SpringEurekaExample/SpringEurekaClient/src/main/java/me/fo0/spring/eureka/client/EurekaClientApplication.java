package me.fo0.spring.eureka.client;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class EurekaClientApplication {

	@Autowired
	private WebClient.Builder client;

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApplication.class, args);
	}

	@RequestMapping("/test")
	public String home() {
		return "Hello World";
	}

	@PostConstruct
	public void run() {
		System.out.println("test: " + client.baseUrl("http://server:8761/").build()
				.get()
				.uri("test")
				.retrieve()
				.bodyToMono(String.class)
				.block());
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder createBuilder() {
		return WebClient.builder();
	}

}
