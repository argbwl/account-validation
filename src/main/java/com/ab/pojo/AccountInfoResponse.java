package com.ab.pojo;

import com.ab.api.APIResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class AccountInfoResponse implements APIResponse{

	@JsonProperty("header")
	private CommonHeader header;

	@JsonProperty("id")
	private int id;
	@JsonProperty("accStatus")
	private String accountStatus;
	@JsonProperty("accNo")
	private String accountNumber;
	@JsonProperty("accName")
	private String accountName;

	public CommonHeader getHeader() {
		return header;
	}

	public void setHeader(CommonHeader header) {
		this.header = header;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Override
	public String toString() {
		return "AccountInfo [header=" + header + ", id=" + id + ", accountStatus=" + accountStatus + ", accountNumber="
				+ accountNumber + ", accountName=" + accountName + "]";
	}

}
