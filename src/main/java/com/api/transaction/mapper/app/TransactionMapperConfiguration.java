package com.api.transaction.mapper.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TransactionMapperConfiguration {
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

}
