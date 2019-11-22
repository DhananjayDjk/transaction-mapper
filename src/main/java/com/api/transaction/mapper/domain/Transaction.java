package com.api.transaction.mapper.domain;

import java.io.Serializable;

public class Transaction implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private ThisAccount this_account;
	
	private OtherAccount other_account;
	
	private Details details;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ThisAccount getThis_account() {
		return this_account;
	}

	public void setThis_account(ThisAccount this_account) {
		this.this_account = this_account;
	}

	public OtherAccount getOther_account() {
		return other_account;
	}

	public void setOther_account(OtherAccount other_account) {
		this.other_account = other_account;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

}
