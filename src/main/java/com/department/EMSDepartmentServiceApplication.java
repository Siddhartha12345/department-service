package com.department;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.department", "com.ems.common"})
public class EMSDepartmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EMSDepartmentServiceApplication.class, args);
	}

}
