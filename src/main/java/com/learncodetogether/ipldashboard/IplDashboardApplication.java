package com.learncodetogether.ipldashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IplDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(IplDashboardApplication.class, args);
		System.out.println("Main Current Thread " + Thread.currentThread().getName() + " --> " + Thread.currentThread().getId());
	}

}
