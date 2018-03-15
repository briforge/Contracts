package com.presto.contracts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ContractsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractsApplication.class, args);
	}
}
