package com.ab.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.ab.util.JsonUtil;

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
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return accountInfo;
	}
	
	@PostMapping("/fch-nw-act")
	public String fetchNewAccountNumber(@RequestBody OpenAccountInfo openAccountInfo) throws ExternalSystemException {
		logger.info("Received account Number creation request for New Account Number {}",openAccountInfo);
		return accountInfoService.fetchNewAccountNumber(openAccountInfo);
	}
	
	@GetMapping("/acc-info-list-for-close")
	public ResponseEntity<List<AccountInfo>> viewAccountInfoListForClose() throws AccountValidationException{
		return new ResponseEntity<List<AccountInfo>>(accountInfoService.getAccountInfoListForClose(), HttpStatus.OK);
	}
	
	@PostMapping("/close-act")
	public ResponseEntity<String> closeAcct(@RequestBody String actNumber) throws AccountValidationException{
		//TODO need to re-factor and update in DB
		logger.info("Received request to close account for accountNo {}",actNumber);
		String closingStatus = JsonUtil.toJson("InProcess");
		logger.info(closingStatus);
		accountInfoService.updateAccountInfoClosingStatusByActNum("Closing",actNumber);
		return new ResponseEntity<String>(closingStatus, HttpStatus.OK);
	}
	
	@GetMapping("/verify-mo-act/{contactNo}")
	public ResponseEntity<Boolean> verifyActByContactNo(@PathVariable String contactNo) throws AccountValidationException{
		logger.info("Received request to verify account for contactNo {}",contactNo);
		boolean accountStatus = accountInfoService.isAccountExistByContactNo(contactNo);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		logger.info("Verified account by mobileNo {}",accountStatus);
		return new ResponseEntity<Boolean>(accountStatus, HttpStatus.OK);
	}
	

}
