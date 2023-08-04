package com.myblogrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyblogRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyblogRestApiApplication.class, args);
		System.out.println("service running succesfully");
	}

}
