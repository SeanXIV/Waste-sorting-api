package com.enviro.assessment.grad001.andrewseanego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		System.out.println("Server Running at:" + " http://localhost:8080/");
		System.out.println("Use readme for my api Endpoints!");
		System.out.println("Here's an example of how to run disposal guidelines Endpoint:" + " http://localhost:8080/api/disposal-guidelines");
	}

}
