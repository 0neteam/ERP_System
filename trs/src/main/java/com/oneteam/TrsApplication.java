package com.oneteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrsApplication.class, args);
	}

}