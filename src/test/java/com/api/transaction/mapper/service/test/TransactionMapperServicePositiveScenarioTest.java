package com.api.transaction.mapper.service.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.api.transaction.mapper.domain.Details;
import com.api.transaction.mapper.domain.OpenBankTransactionResponse;
import com.api.transaction.mapper.domain.Transaction;
import com.api.transaction.mapper.domain.TransactionMapperResult;
import com.api.transaction.mapper.domain.Value;
import com.api.transaction.mapper.service.TransactionMapperService;

@RunWith(MockitoJUnitRunner.class)
public class TransactionMapperServicePositiveScenarioTest {

	private static final Logger logger = LogManager.getLogger(TransactionMapperServicePositiveScenarioTest.class);

	public final String URI = "https://apisandbox.openbankproject.com/obp/v1.2.1/banks/rbs/accounts/savings-kids-john/public/transactions";

	@InjectMocks
	TransactionMapperService transactionMapperService;

	@Mock
	RestTemplate restTemplate;

	@Mock
	Environment env;

	OpenBankTransactionResponse openBankTransactionResponse;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		openBankTransactionResponse = prepareMockOpenApiTransactionResponse();
	}

	@Test
	public void testGetAllTransactions() {
		Mockito.when(env.getProperty("open.bank.api.url")).thenReturn(URI);
		Mockito.when(restTemplate.getForObject(URI, OpenBankTransactionResponse.class)).thenReturn(openBankTransactionResponse);
		TransactionMapperResult response = transactionMapperService.getAllTransactions();
		logger.info("All transactions list size is..." + response.getTransformedTransactions().size());
		Assert.assertEquals(3, response.getTransformedTransactions().size());
	}

	@Test
	public void testGetTransactionsByType() {
		Mockito.when(env.getProperty("open.bank.api.url")).thenReturn(URI);
		Mockito.when(restTemplate.getForObject(URI, OpenBankTransactionResponse.class)).thenReturn(openBankTransactionResponse);
		TransactionMapperResult response = transactionMapperService.getTransactionsByType("SANDBOX_TAN");
		logger.info("All transactions list size for transaction type SANDBOX_TAN is..." + response.getTransformedTransactions().size());
		Assert.assertEquals(2, response.getTransformedTransactions().size());
	}

	@Test
	public void testGetTransactionAmountByType() {
		Mockito.when(env.getProperty("open.bank.api.url")).thenReturn(URI);
		Mockito.when(restTemplate.getForObject(URI, OpenBankTransactionResponse.class)).thenReturn(openBankTransactionResponse);
		String response = transactionMapperService.getTransactionAmountByType("SANDBOX_TAN");
		logger.info("Total transaction amount for transaction type SANDBOX_TAN is..." + response);
		Assert.assertEquals("50", response);
	}

	private OpenBankTransactionResponse prepareMockOpenApiTransactionResponse() {
		OpenBankTransactionResponse transactionResponse = new OpenBankTransactionResponse();
		List<Transaction> transactionlist = new ArrayList<>();
		Transaction transaction1 = new Transaction();
		Details details1 = new Details();
		details1.setType("SANDBOX_TAN");
		Value value1 = new Value();
		value1.setAmount("20");
		details1.setValue(value1);
		transaction1.setDetails(details1);
		transactionlist.add(transaction1);

		Transaction transaction2 = new Transaction();
		Details details2 = new Details();
		details2.setType("SANDBOX_TAN");
		Value value2 = new Value();
		value2.setAmount("30");
		details2.setValue(value2);
		transaction2.setDetails(details2);
		transactionlist.add(transaction2);

		Transaction transaction3 = new Transaction();
		Details details3 = new Details();
		details3.setType("SANDBOX_NEW");
		Value value3 = new Value();
		value3.setAmount("60");
		details3.setValue(value3);
		transaction3.setDetails(details3);
		transactionlist.add(transaction3);

		transactionResponse.setTransactions(transactionlist);
		return transactionResponse;
	}

}
