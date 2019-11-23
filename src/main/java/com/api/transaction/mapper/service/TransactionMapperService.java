package com.api.transaction.mapper.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.api.transaction.mapper.domain.OpenBankTransactionResponse;
import com.api.transaction.mapper.domain.Transaction;
import com.api.transaction.mapper.domain.TransactionMapperResult;
import com.api.transaction.mapper.domain.TransformedTransaction;

@Service
public class TransactionMapperService {

	private static final Logger logger = LogManager.getLogger(TransactionMapperService.class);

	@Autowired
	private Environment env;

	@Autowired
	RestTemplate restTemplate;

	public TransactionMapperResult getAllTransactions() {
		logger.info("Fetching all transactions...");
		OpenBankTransactionResponse openBankTransactionResponse = getOpenBankTransactions();
		TransactionMapperResult transactionMapperResult = getAllTransactions(openBankTransactionResponse);
		return transactionMapperResult;
	}

	public TransactionMapperResult getTransactionsByType(String transactionType) {
		logger.info("Fetching all transactions for transaction type " + transactionType);
		OpenBankTransactionResponse openBankTransactionResponse = getOpenBankTransactions();
		TransactionMapperResult transactionMapperResult = getAllTransactionsByType(openBankTransactionResponse, transactionType);
		return transactionMapperResult;
	}

	public String getTransactionAmountByType(String transactionType) {
		logger.info("Fetching total amount for transaction type " + transactionType);
		OpenBankTransactionResponse openBankTransactionResponse = getOpenBankTransactions();
		String amount = getTransactionAmountByType(openBankTransactionResponse, transactionType);
		return amount;
	}

	private OpenBankTransactionResponse getOpenBankTransactions() {
		logger.info("Calling open bank api service...");
		OpenBankTransactionResponse transactionResponse = restTemplate.getForObject(env.getProperty("open.bank.api.url"), OpenBankTransactionResponse.class);
		return transactionResponse;
	}

	private String getTransactionAmountByType(OpenBankTransactionResponse openBankTransactionResponse, String transactionType) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		List<Transaction> transactionList = openBankTransactionResponse.getTransactions();
		if (!CollectionUtils.isEmpty(transactionList)) {
			logger.debug("Open api transaction list size is..." + transactionList.size());
			for (Transaction transaction : transactionList) {
				if ((null == transaction.getDetails()) || (null != transaction.getDetails() && !transactionType.equals(transaction.getDetails().getType()))) {
					continue;
				}
				if (null != transaction.getDetails() && null != transaction.getDetails().getValue()) {
					BigDecimal transactionAmount = new BigDecimal(transaction.getDetails().getValue().getAmount());
					totalAmount = totalAmount.add(transactionAmount);
				}
			}
		}
		logger.debug("Total amount is..." + totalAmount.toString());
		return totalAmount.toString();
	}

	private TransactionMapperResult getAllTransactionsByType(OpenBankTransactionResponse openBankTransactionResponse, String transactionType) {
		TransactionMapperResult transactionResult = new TransactionMapperResult();
		List<TransformedTransaction> transformedTransactions = new ArrayList<>();
		List<Transaction> transactionList = openBankTransactionResponse.getTransactions();
		if (!CollectionUtils.isEmpty(transactionList)) {
			logger.debug("Open api transaction list size is..." + transactionList.size());
			for (Transaction transaction : transactionList) {
				if ((null == transaction.getDetails()) || (null != transaction.getDetails() && !transactionType.equals(transaction.getDetails().getType()))) {
					continue;
				}
				prepareResponse(transformedTransactions, transaction);
			}
		}
		logger.debug("transformed transaction list size is..." + transformedTransactions.size());
		transactionResult.setTransformedTransactions(transformedTransactions);
		return transactionResult;
	}

	private TransactionMapperResult getAllTransactions(OpenBankTransactionResponse openBankTransactionResponse) {
		TransactionMapperResult transactionResult = new TransactionMapperResult();
		List<TransformedTransaction> transformedTransactions = new ArrayList<>();
		List<Transaction> transactionList = openBankTransactionResponse.getTransactions();
		if (!CollectionUtils.isEmpty(transactionList)) {
			logger.debug("Open api transaction list size is..." + transactionList.size());
			for (Transaction transaction : transactionList) {
				prepareResponse(transformedTransactions, transaction);
			}
		}
		logger.debug("transformed transaction list size is..." + transformedTransactions.size());
		transactionResult.setTransformedTransactions(transformedTransactions);
		return transactionResult;
	}

	private void prepareResponse(List<TransformedTransaction> transformedTransactions, Transaction transaction) {
		TransformedTransaction transformedTransaction = new TransformedTransaction();
		transformedTransaction.setId(transaction.getId());
		if (null != transaction.getThis_account()) {
			transformedTransaction.setAccountId(transaction.getThis_account().getId());
		}
		if (null != transaction.getOther_account()) {
			transformedTransaction.setCounterpartyAccount(transaction.getOther_account().getNumber());
			if (null != transaction.getOther_account().getHolder()) {
				transformedTransaction.setCounterpartyName(transaction.getOther_account().getHolder().getName());
			}
			if (null != transaction.getOther_account().getMetadata()) {
				transformedTransaction.setCounterpartyLogoPath(transaction.getOther_account().getMetadata().getImage_URL());
			}
		}
		if (null != transaction.getDetails()) {
			transformedTransaction.setTransactionType(transaction.getDetails().getType());
			transformedTransaction.setDescription(transaction.getDetails().getDescription());
			if (null != transaction.getDetails().getValue()) {
				transformedTransaction.setInstructedCurrency(transaction.getDetails().getValue().getCurrency());
				transformedTransaction.setTransactionCurrency(transaction.getDetails().getValue().getCurrency());
				transformedTransaction.setInstructedAmount(transaction.getDetails().getValue().getAmount());
				transformedTransaction.setTransactionAmount(transaction.getDetails().getValue().getAmount());
			}
		}
		transformedTransactions.add(transformedTransaction);
	}

}
