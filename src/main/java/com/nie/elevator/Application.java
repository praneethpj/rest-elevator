package com.nie.elevator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Application {

	public static void main(String[] args) {
		System.out.println("Elevator Application starts to run.");
		SpringApplication.run(Application.class, args);
	}
}
