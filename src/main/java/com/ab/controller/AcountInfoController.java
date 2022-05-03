package com.ab.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ab.exception.AccountValidationException;
import com.ab.exception.ExternalSystemException;
import com.ab.logger.AVLogger;
import com.ab.pojo.AccountInfo;
import com.ab.service.IAccountInfoService;
import com.ab.ui.pojo.OpenAccountInfo;

@RestController
@RequestMapping("aic")
@CrossOrigin
public class AcountInfoController {
	
	private static final AVLogger logger = AVLogger.getLogger(AcountInfoController.class);
	
	@Autowired
	private IAccountInfoService accountInfoService;
	
	@GetMapping("/acc-info-list")
	public ResponseEntity<List<AccountInfo>> viewAccountInfoList() throws AccountValidationException{
		return new ResponseEntity<List<AccountInfo>>(accountInfoService.getAccountInfoList(), HttpStatus.OK);
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/acc-info-dtlist")
	public List<AccountInfo> viewAccountInfoListByDate(){
		Date d = new Date();
		return accountInfoService.getAccountInfoListByDate(new Date(d.getYear(),d.getMonth(),d.getDate()));
	}
	
	@PostMapping("/open-act")
	public AccountInfo openNewAccount(@RequestBody @Valid OpenAccountInfo openAccountInfo) throws ExternalSystemException {
		logger.info("Request Received to open new Account {}",openAccountInfo);
		AccountInfo accountInfo = accountInfoService.openNewAccount(openAccountInfo);
		logger.info("Account opened for {}",accountInfo);
		
		return accountInfo;
	}
	
	@PostMapping("/fch-nw-act")
	public String fetchNewAccountNumber(@RequestBody OpenAccountInfo openAccountInfo) throws ExternalSystemException {
		logger.info("Received account Number creation request for New Account Number {}",openAccountInfo);
		return accountInfoService.fetchNewAccountNumber(openAccountInfo);
	}

}
