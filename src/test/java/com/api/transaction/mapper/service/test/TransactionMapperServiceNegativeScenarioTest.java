package com.api.transaction.mapper.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.api.transaction.mapper.domain.OpenBankTransactionResponse;
import com.api.transaction.mapper.domain.Transaction;
import com.api.transaction.mapper.domain.TransactionMapperResult;
import com.api.transaction.mapper.exception.OpenBankTransactionNotFoundException;
import com.api.transaction.mapper.service.TransactionMapperService;

@RunWith(MockitoJUnitRunner.class)
public class TransactionMapperServiceNegativeScenarioTest {
	
public final String URI = "https://apisandbox.openbankproject.com/obp/v1.2.1/banks/rbs/accounts/savings-kids-john/public/transactions";
	
	@InjectMocks
	TransactionMapperService transactionMapperService;
	
	@Mock
	RestTemplate restTemplate;
	
	OpenBankTransactionResponse openBankTransactionResponse;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		openBankTransactionResponse = prepareMockOpenApiTransactionResponse();
	}
	
	@Test(expected = OpenBankTransactionNotFoundException.class)
	public void testOpenBankTransactionNotFoundExceptionForGetAllTransactions() {
		Mockito.when(restTemplate.getForObject(URI, OpenBankTransactionResponse.class)).thenReturn(openBankTransactionResponse);	
		TransactionMapperResult response = transactionMapperService.getAllTransactions();
		System.out.println("All transactions list size is..."+response.getTransformedTransactions().size());
	}
	
	@Test(expected = OpenBankTransactionNotFoundException.class)
	public void testOpenBankTransactionNotFoundExceptionForGetTransactionsByType() {
		Mockito.when(restTemplate.getForObject(URI, OpenBankTransactionResponse.class)).thenReturn(openBankTransactionResponse);	
		TransactionMapperResult response = transactionMapperService.getTransactionsByType("SANDBOX_TAN");
		System.out.println("Transaction type list size is..."+response.getTransformedTransactions().size());
	}
	
	@Test(expected = OpenBankTransactionNotFoundException.class)
	public void testOpenBankTransactionNotFoundExceptionForGetTransactionAmountByType() {
		Mockito.when(restTemplate.getForObject(URI, OpenBankTransactionResponse.class)).thenReturn(openBankTransactionResponse);	
		String response = transactionMapperService.getTransactionAmountByType("SANDBOX_TAN");
		System.out.println("Transaction amount is..."+response);
	}
	
	private OpenBankTransactionResponse prepareMockOpenApiTransactionResponse() {
		OpenBankTransactionResponse transactionResponse = new OpenBankTransactionResponse();
		List<Transaction> transactionlist = new ArrayList<>();		
		transactionResponse.setTransactions(transactionlist);
		return transactionResponse;
	}

}
