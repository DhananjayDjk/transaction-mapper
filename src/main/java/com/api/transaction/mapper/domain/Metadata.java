package com.api.transaction.mapper.domain;

import java.io.Serializable;

public class Metadata implements Serializable {

	private static final long serialVersionUID = 1L;

	private String image_URL;

	public String getImage_URL() {
		return image_URL;
	}

	public void setImage_URL(String image_URL) {
		this.image_URL = image_URL;
	}

}
