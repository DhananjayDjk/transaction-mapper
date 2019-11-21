package com.api.transaction.mapper.domain;

import java.util.List;

public class OpenBankTransactionResponse {
	
	private List<Transaction> transactions;

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
