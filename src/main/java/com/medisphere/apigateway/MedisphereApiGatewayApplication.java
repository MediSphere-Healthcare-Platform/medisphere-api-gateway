package com.medisphere.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MedisphereApiGatewayApplication {

	public static void main(String[] args) {
		System.out.println("GATEWAY STARTING");
		SpringApplication.run(MedisphereApiGatewayApplication.class, args);
	}

}
