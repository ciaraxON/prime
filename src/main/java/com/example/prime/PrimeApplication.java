package com.example.prime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PrimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimeApplication.class, args);
	}

}
