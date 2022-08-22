package com.ab.service;

import java.util.Date;
import java.util.List;

import com.ab.entity.AccountInfoEntity;
import com.ab.exception.AccountValidationException;
import com.ab.exception.ExternalSystemException;
import com.ab.pojo.AccountInfo;
import com.ab.ui.pojo.OpenAccountInfo;

public interface IAccountInfoService {

	public AccountInfoEntity saveAccountInfo(AccountInfo accountInfo);
	public AccountInfo getAccountInfoByAccNumber(AccountInfo accountInfo);
	public AccountInfo getActivecAcountInfoByAccNumber(AccountInfo accountInfo);
	public int updateAcountInfoStatusById(int id, String status);
	public AccountInfo getAccountInfoByAccountNumber(String accountNumber);
	public AccountInfoEntity getAccountByStaus(String accNo, String status);
	public List<AccountInfo> getAccountInfoList() throws AccountValidationException;
	public List<AccountInfo> getAccountInfoListForClose() throws AccountValidationException;
	public List<AccountInfo> getAccountInfoListByDate(Date date);
	public AccountInfo openNewAccount(OpenAccountInfo openAccountInfo) throws ExternalSystemException;
	public String fetchNewAccountNumber(OpenAccountInfo openAccountInfo) throws ExternalSystemException;
	public List<AccountInfo> getAccountInfoListByDateAndStatus(Date days, String status);
	public List<AccountInfo> updateAccountInfoListStatusByDate(Date days, String findingStatus, String updatingStatus);
	public int updateAccountInfoClosingStatusByActNum(String string, String actNumber);
	public boolean isAccountExistByContactNo(String contactNo);
	

	
}
