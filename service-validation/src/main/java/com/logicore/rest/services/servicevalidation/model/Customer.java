package com.logicore.rest.services.servicevalidation.model;

import java.io.Serializable;

public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private int age;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
