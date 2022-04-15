package com.ab.service;

import com.ab.exception.AccountValidationException;
import com.ab.pojo.AccountAuth;
import com.ab.pojo.AccountAuthResponse;

public interface IAccountAuthService {

	public AccountAuth authorizeAccount(AccountAuth accAuth) throws AccountValidationException;
	public AccountAuth approveAccount(AccountAuth accAuth);
	public AccountAuthResponse rejectAccount(AccountAuth accAuth);
	
}
