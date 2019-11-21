package com.api.transaction.mapper.domain;

import java.util.List;

public class TransactionMapperResult {
	
	private List<TransformedTransaction> transformedTransactions;

	public List<TransformedTransaction> getTransformedTransactions() {
		return transformedTransactions;
	}

	public void setTransformedTransactions(List<TransformedTransaction> transformedTransactions) {
		this.transformedTransactions = transformedTransactions;
	}

}
