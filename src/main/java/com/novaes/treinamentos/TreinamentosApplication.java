package com.novaes.treinamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TreinamentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreinamentosApplication.class, args);
		System.out.println("=== Startup Completed ===");
	}

}
