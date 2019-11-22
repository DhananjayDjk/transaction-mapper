package com.api.transaction.mapper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.transaction.mapper.domain.TransactionMapperResult;
import com.api.transaction.mapper.service.TransactionMapperService;

@RestController
@RequestMapping(path = "/transactions")
public class TransactionMapperController {
	
	@Autowired
	TransactionMapperService transactionMapperService;
	
	@GetMapping(path="/all", produces = "application/json")
    public TransactionMapperResult getAllTransactions() {	
		return transactionMapperService.getAllTransactions();
	}
	
	@GetMapping(path="/{transactionType}/all", produces = "application/json")
    public TransactionMapperResult getTransactionsByType(@PathVariable String transactionType) {
		return transactionMapperService.getTransactionsByType(transactionType);
	}
	
	@GetMapping(path="/{transactionType}/amount", produces = "application/json")
    public String getTransactionAmountByType(@PathVariable String transactionType) {
		return transactionMapperService.getTransactionAmountByType(transactionType);
	}

}
