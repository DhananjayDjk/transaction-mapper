package com.api.transaction.mapper.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.api.transaction.mapper"})
public class TransactionMapperApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TransactionMapperApplication.class, args);
	}
	

}
