package com.ab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ab.api.APIResponse;
import com.ab.exception.AccountValidationException;
import com.ab.logger.AVLogger;
import com.ab.pojo.AccountAuth;
import com.ab.pojo.AccountAuthResponse;
import com.ab.service.IAccountAuthService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("ua")
public class AccountAuthController {

	private static final AVLogger logger = AVLogger.getLogger(AccountAuthController.class);

	@Autowired
	private IAccountAuthService accountAuthService;

	@PostMapping("/auth-acc")
	@ApiOperation(value = "Authorize The Account")
	public ResponseEntity<String> authorizeAccount(@RequestBody AccountAuth accAuth) throws AccountValidationException {
		logger.info("Starting Account Authorization");
		AccountAuth accountAuth;
		accountAuth = accountAuthService.authorizeAccount(accAuth);
		if (null != accountAuth) {
			logger.info("Account ID " + accountAuth.getAccId() + " Authorized with Status " + accountAuth.getStatus());
			return new ResponseEntity<String>("Account ID " + accountAuth.getAccId() + " Authorized with Status " + accountAuth.getStatus(),HttpStatus.OK);
		}
		logger.warn("End Account Authorization with No Content");
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/approve-acc")
	public String approveAccount(@RequestBody AccountAuth accAuth) {
		AccountAuth accountAuth = accountAuthService.approveAccount(accAuth);
		if (null != accountAuth) {
			return "Account ID " + accountAuth.getAccId() + " Authorized with Status " + accountAuth.getStatus();
		}
		return "No Valid Account Found with Account Number " + accAuth.getAccountNumber();
	}

	@PostMapping("/reject-acc")
	public APIResponse rejectAccount(@RequestBody AccountAuth accAuth) {
		AccountAuthResponse accountAuth = accountAuthService.rejectAccount(accAuth);
		if (null != accountAuth) {
			logger.info("Account ID " + accountAuth.getAccId() + " Authorized with Status " + accountAuth.getStatus());
			return accountAuth;
		}
		logger.info("No Valid Account Found with Account Number " + accAuth.getAccountNumber());
		return accountAuth;
	}

}
