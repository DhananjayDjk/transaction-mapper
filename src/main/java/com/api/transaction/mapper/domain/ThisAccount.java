package com.api.transaction.mapper.domain;

import java.io.Serializable;

public class ThisAccount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
