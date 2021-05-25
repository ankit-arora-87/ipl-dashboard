package com.learncodetogether.ipldashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.learncodetogether.ipldashboard"})
public class IplDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(IplDashboardApplication.class, args);
		System.out.println("Main Current Thread " + Thread.currentThread().getName() + " --> " + Thread.currentThread().getId());
	}

}
