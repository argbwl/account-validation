package com.ab.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class OpenAccountRequest {

	@JsonProperty("header")
	private CommonHeader header;
	
	private String city;
	private String state;
	
	public CommonHeader getHeader() {
		return header;
	}
	public void setHeader(CommonHeader header) {
		this.header = header;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
	
	
}