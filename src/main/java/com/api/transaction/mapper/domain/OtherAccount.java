package com.api.transaction.mapper.domain;

import java.io.Serializable;

public class OtherAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	private String number;

	private Holder holder;

	private Metadata metadata;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Holder getHolder() {
		return holder;
	}

	public void setHolder(Holder holder) {
		this.holder = holder;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

}
