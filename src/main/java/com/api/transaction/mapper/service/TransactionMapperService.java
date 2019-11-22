package com.api.transaction.mapper.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.transaction.mapper.domain.OpenBankTransactionResponse;
import com.api.transaction.mapper.domain.Transaction;
import com.api.transaction.mapper.domain.TransactionMapperResult;
import com.api.transaction.mapper.domain.TransformedTransaction;
import com.api.transaction.mapper.util.TransactionMapperHelper;

@Service
public class TransactionMapperService {
	
	@Autowired
	private Environment env;
	
	@Autowired
	RestTemplate restTemplate;
	
	public TransactionMapperResult getAllTransactions() {
		OpenBankTransactionResponse openBankTransactionResponse = getOpenBankTransactions();
		TransactionMapperResult transactionMapperResult = getAllTransactions(openBankTransactionResponse);
        return transactionMapperResult;
    }
	
	public TransactionMapperResult getTransactionsByType(String transactionType) {		
		OpenBankTransactionResponse openBankTransactionResponse = getOpenBankTransactions();
		TransactionMapperResult transactionMapperResult = getAllTransactionsByType(openBankTransactionResponse, transactionType);
        return transactionMapperResult;
    }
	
	public String getTransactionAmountByType(String transactionType) {
		OpenBankTransactionResponse openBankTransactionResponse = getOpenBankTransactions();
		String amount = getTransactionAmountByType(openBankTransactionResponse, transactionType);
        return amount;
    }
	
	private OpenBankTransactionResponse getOpenBankTransactions() {
		OpenBankTransactionResponse transactionResponse = restTemplate.getForObject(env.getProperty("open.bank.api.url"), OpenBankTransactionResponse.class);
		TransactionMapperHelper.validateOpenBankTransactionResponse(transactionResponse);
		return transactionResponse;
	}
	
    private String getTransactionAmountByType(OpenBankTransactionResponse openBankTransactionResponse, String transactionType) {	
		BigDecimal totalAmount = BigDecimal.ZERO;
		List<Transaction> transactionList = openBankTransactionResponse.getTransactions();	    
	    for(Transaction transaction : transactionList) { 	
	    	if((null == transaction.getDetails()) || (null != transaction.getDetails() && !transactionType.equals(transaction.getDetails().getType()))) {
	    		continue;
	    	}    	    	
	    	if(null != transaction.getDetails()) {
	    		if(null != transaction.getDetails().getValue()) {
		    		BigDecimal transactionAmount = new BigDecimal(transaction.getDetails().getValue().getAmount());
		    		totalAmount = totalAmount.add(transactionAmount);
		    	}
	    	}    	
	    }    
		return totalAmount.toString();
	}
    
    private TransactionMapperResult getAllTransactionsByType(OpenBankTransactionResponse openBankTransactionResponse, String transactionType) {
		TransactionMapperResult transactionResult = new TransactionMapperResult();
		List<TransformedTransaction> transformedTransactions = new ArrayList<>();
		List<Transaction> transactionList = openBankTransactionResponse.getTransactions();	    
	    for(Transaction transaction : transactionList) {
	    	if((null == transaction.getDetails()) || (null != transaction.getDetails() && !transactionType.equals(transaction.getDetails().getType()))) {
	    		continue;
	    	}
	    	prepareResponse(transformedTransactions, transaction);    	
	    }   
	    transactionResult.setTransformedTransactions(transformedTransactions);
		return transactionResult;
	}
    
    private TransactionMapperResult getAllTransactions(OpenBankTransactionResponse openBankTransactionResponse) {
		TransactionMapperResult transactionResult = new TransactionMapperResult();
		List<TransformedTransaction> transformedTransactions = new ArrayList<>();
		List<Transaction> transactionList = openBankTransactionResponse.getTransactions();    
	    for(Transaction transaction : transactionList) {
	    	prepareResponse(transformedTransactions, transaction);
	    }    
	    transactionResult.setTransformedTransactions(transformedTransactions);
		return transactionResult;
	}

	private void prepareResponse(List<TransformedTransaction> transformedTransactions, Transaction transaction) {
		TransformedTransaction  transformedTransaction = new TransformedTransaction();
		transformedTransaction.setId(transaction.getId());
		if(null != transaction.getThis_account()) {
			transformedTransaction.setAccountId(transaction.getThis_account().getId());
		}
		if(null != transaction.getOther_account()) {
			transformedTransaction.setCounterpartyAccount(transaction.getOther_account().getNumber());
			if(null != transaction.getOther_account().getHolder()) {
				transformedTransaction.setCounterpartyName(transaction.getOther_account().getHolder().getName());
			}
			if(null != transaction.getOther_account().getMetadata()) {
				transformedTransaction.setCounterpartyLogoPath(transaction.getOther_account().getMetadata().getImage_URL());
			}
		}
		if(null != transaction.getDetails()) {
			transformedTransaction.setTransactionType(transaction.getDetails().getType());
			transformedTransaction.setDescription(transaction.getDetails().getDescription());
			if(null != transaction.getDetails().getValue()) {
				transformedTransaction.setInstructedCurrency(transaction.getDetails().getValue().getCurrency());
				transformedTransaction.setTransactionCurrency(transaction.getDetails().getValue().getCurrency());
				transformedTransaction.setInstructedAmount(transaction.getDetails().getValue().getAmount());
				transformedTransaction.setTransactionAmount(transaction.getDetails().getValue().getAmount());
			}
		} 	
		transformedTransactions.add(transformedTransaction);
	}

}
