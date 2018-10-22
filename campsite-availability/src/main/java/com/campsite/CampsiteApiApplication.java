package com.campsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CampsiteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampsiteApiApplication.class, args);
	}
}
