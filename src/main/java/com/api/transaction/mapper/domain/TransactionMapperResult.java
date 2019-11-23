package com.api.transaction.mapper.domain;

import java.io.Serializable;
import java.util.List;

public class TransactionMapperResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<TransformedTransaction> transformedTransactions;

	public List<TransformedTransaction> getTransformedTransactions() {
		return transformedTransactions;
	}

	public void setTransformedTransactions(List<TransformedTransaction> transformedTransactions) {
		this.transformedTransactions = transformedTransactions;
	}

}
