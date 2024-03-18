package com.department;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EMSDepartmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EMSDepartmentServiceApplication.class, args);
	}

}
