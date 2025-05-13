package com.oneteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MfrApplication {

	public static void main(String[] args) {
		SpringApplication.run(MfrApplication.class, args);
	}

}
