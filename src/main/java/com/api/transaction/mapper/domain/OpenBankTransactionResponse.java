package com.api.transaction.mapper.domain;

import java.io.Serializable;
import java.util.List;

public class OpenBankTransactionResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Transaction> transactions;

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
