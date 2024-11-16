package com.jagija.smileapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmileappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmileappApplication.class, args);
	}

}
