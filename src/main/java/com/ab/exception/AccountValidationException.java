package com.ab.exception;

import org.springframework.http.HttpStatus;

public class AccountValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2916945375163178078L;
	private String message;
	private String details;
	private HttpStatus httpStatus;
	private String errorCode;

	public AccountValidationException() {
		super();
	}

	public AccountValidationException(String message, String details, HttpStatus httpStatus, String errorCode) {
		super();
		this.message = message;
		this.details = details;
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
