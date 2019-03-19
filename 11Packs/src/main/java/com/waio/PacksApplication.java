package com.waio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/*
 * Author: Viram Dhangar
 * This class is a spring boot configuration class
 * */
@SpringBootApplication
@EnableScheduling
public class PacksApplication {

	public static void main(String[] args) {
		SpringApplication.run(PacksApplication.class, args);
	}
}
