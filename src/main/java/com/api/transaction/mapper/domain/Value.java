package com.api.transaction.mapper.domain;

import java.io.Serializable;

public class Value implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String currency;
	
	private String amount;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
