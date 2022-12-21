package com.logicore.rest.services.servicetransformation.model;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;

	private String type;
	private int discount;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}