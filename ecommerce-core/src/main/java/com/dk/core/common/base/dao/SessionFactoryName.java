package com.dk.core.common.base.dao;

public enum SessionFactoryName {
	
	SessionFactory("sessionFactory");
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private SessionFactoryName(String name) {
		this.name = name;
	}
	
}
