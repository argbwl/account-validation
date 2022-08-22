package com.ab.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class AccountInfo {

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
	@JsonProperty("closingStatus")
	private String closingStatus;

	private String checkStatus;
	private String accId;

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

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}
	
	public String getClosingStatus() {
		return closingStatus;
	}

	public void setClosingStatus(String closingStatus) {
		this.closingStatus = closingStatus;
	}

	@Override
	public String toString() {
		return "AccountInfo [header=" + header + ", id=" + id + ", accountStatus=" + accountStatus + ", accountNumber="
				+ accountNumber + ", accountName=" + accountName + ", closingStatus=" + closingStatus + ", checkStatus="
				+ checkStatus + ", accId=" + accId + "]";
	}

}
