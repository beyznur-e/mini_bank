package com.example.mini_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // Caching mekanizmasını aktif eder
public class MiniBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniBankApplication.class, args);
	}

}
