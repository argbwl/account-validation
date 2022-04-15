package com.ab.enumpkg;

public enum StatusEnum {
	
	active("ACTIVE"),
	inactive("INACTIVE"),
	reject("REJECT"),
	pending("PENDING"),
	approved("APPROVED"),
	authpend("AUTHPEND"),
	system("SYSTEM"),
	insert("INSERT");
	
	private String status;
	
	StatusEnum(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}

}
