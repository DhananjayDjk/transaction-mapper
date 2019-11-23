package com.api.transaction.mapper.domain;

import java.io.Serializable;

public class Details implements Serializable {

	private static final long serialVersionUID = 1L;

	private Value value;

	private String type;

	private String description;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

}
