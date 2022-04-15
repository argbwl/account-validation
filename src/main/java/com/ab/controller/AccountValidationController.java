package com.ab.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ab.api.APIResponse;
import com.ab.pojo.AccountInfo;
import com.ab.service.IAccountInfoService;
import com.ab.util.AccountUtil;

@RestController
@RequestMapping("av")
public class AccountValidationController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountValidationController.class);
	
	@Autowired
	private IAccountInfoService accountInfoService;

	@PostMapping("/validate-account")
	public String validateAccount(@RequestBody AccountInfo accountInfo) {
		logger.info("recived account {} ",accountInfo.toString());
		AccountInfo af = accountInfoService.getActivecAcountInfoByAccNumber(accountInfo);
		if(null!=af && StringUtils.isNotBlank(af.getAccountName())) {
			return af.getAccountName();
		}
		return "Inavlid Account Number";
	}
	
	@PostMapping("/upload-act")
	public String uploadAccount(@RequestBody AccountInfo accountInfo) {
		logger.info("recived account {}",accountInfo.toString());
		if(AccountUtil.validateAccountUpload(accountInfo)) {
			logger.info("Account Validated");
			AccountInfo info = accountInfoService.getAccountInfoByAccountNumber(accountInfo.getAccountNumber());
			if(null!=info && StringUtils.isNotBlank(info.getAccountNumber())) {
				return "Account already Present with this account number";
			}
			int id = accountInfoService.saveAccountInfo(accountInfo).getId();
			return "Account uploaded Succesfully with id "+id;
		}else {
			logger.info("Account Validation failed");
			return "Invalid Account Name/Number";
		}
	}
	
	
	public APIResponse deleteAccount(@RequestBody AccountInfo accountInfo) {
		
		return null;
	}
	
}
