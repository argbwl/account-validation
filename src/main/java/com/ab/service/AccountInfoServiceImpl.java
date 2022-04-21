package com.ab.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ab.constant.AccountValidationConstant;
import com.ab.entity.AccountInfoEntity;
import com.ab.enumpkg.StatusEnum;
import com.ab.exception.AccountValidationException;
import com.ab.pojo.AccountInfo;
import com.ab.repo.AccountInfoRepo;
import com.ab.ui.pojo.OpenAccountInfo;
import com.ab.util.AccountUtil;
import com.ab.util.BankMapUtil;
import com.ab.util.JsonUtil;

@Service
@Transactional
public class AccountInfoServiceImpl implements IAccountInfoService {

	private static final Logger logger = LoggerFactory.getLogger(AccountInfoServiceImpl.class);

	@Autowired
	private AccountInfoRepo accountInfoRepo;

	@Override
	public AccountInfoEntity saveAccountInfo(AccountInfo accountInfo) {
		AccountInfoEntity accountInfoEntity = new AccountInfoEntity();
		accountInfoEntity.setAccountName(accountInfo.getAccountName());
		accountInfoEntity.setAccountNumber(accountInfo.getAccountNumber());
		accountInfoEntity.setAccountStatus(StatusEnum.inactive.getStatus());
		return accountInfoRepo.save(accountInfoEntity);
	}

	@Override
	public AccountInfo getAccountInfoByAccNumber(AccountInfo accountInfo) {
		logger.info("Start Find Details from DB AccountInfo {} ", JsonUtil.toJson(accountInfo));
		AccountInfoEntity accountInfoEntity = getAccountByStaus(accountInfo.getAccountNumber(), accountInfo.getCheckStatus());
		logger.info("Loaded Details from DB {} ",JsonUtil.toJson(accountInfoEntity));
		AccountInfo info = new AccountInfo();
		if (null != accountInfoEntity && accountInfo.getAccountName().equals(accountInfoEntity.getAccountName())) {
			info = new AccountInfo();
			info.setAccountName(accountInfoEntity.getAccountName());
			info.setAccountNumber(accountInfoEntity.getAccountNumber());
			info.setId(accountInfoEntity.getId());
		}
		logger.info("End Find Details from DB");
		return info;
	}

	@Override
	public int updateAcountInfoStatusById(int id, String status) {
		Optional<AccountInfoEntity> accountInfoEntity = accountInfoRepo.findById(id);
		if (accountInfoEntity.isPresent()) {
			accountInfoEntity.get().setModifiedDt(new Date());
			accountInfoEntity.get().setAccountStatus(status);
			accountInfoRepo.save(accountInfoEntity.get());
		}
		return 0;
	}

	@Override
	public AccountInfo getActivecAcountInfoByAccNumber(AccountInfo accountInfo) {
		logger.info("Start Find Details from DB for Active Account");
		AccountInfoEntity accountInfoEntity = accountInfoRepo
				.findByAccountNumberAndAccountStatus(accountInfo.getAccountNumber(),StatusEnum.active.getStatus());
		logger.info("Loaded Details from DB");
		AccountInfo info = new AccountInfo();
		if (null != accountInfoEntity && accountInfo.getAccountName().equals(accountInfoEntity.getAccountName())) {
			info = new AccountInfo();
			info.setAccountName(accountInfoEntity.getAccountName());
			info.setAccountNumber(accountInfoEntity.getAccountNumber());
			info.setId(accountInfoEntity.getId());
		}
		logger.info("End Find Details from DB for Active Account");
		return info;
	}

	@Override
	public AccountInfo getAccountInfoByAccountNumber(String accountNumber) {
		AccountInfoEntity accountInfoEntity = accountInfoRepo.findByAccountNumber(accountNumber);
		AccountInfo accountInfo = new AccountInfo();
		if (null != accountInfoEntity) {
			accountInfo.setAccountName(accountInfoEntity.getAccountName());
			accountInfo.setAccountNumber(accountInfoEntity.getAccountNumber());
		}
		return accountInfo;
	}

	@Override
	public AccountInfoEntity getAccountByStaus(String accNo, String status) {
		AccountInfoEntity accountInfoEntity = accountInfoRepo.findByAccountNumberAndAccountStatus(accNo, status);
		return accountInfoEntity;
		
	}

	@Override
	public List<AccountInfo> getAccountInfoList() throws AccountValidationException {
		List<AccountInfoEntity> actinfoList = accountInfoRepo.findAll();
		if(actinfoList.isEmpty()) {
			throw new AccountValidationException("No Records AVailable", "Empty Records", HttpStatus.PARTIAL_CONTENT, AccountValidationConstant.ERROR_3015);
		}
		List<AccountInfo> infoList = new ArrayList<>();
		actinfoList.forEach(a ->{
			AccountInfo info = new AccountInfo();
			info.setId(a.getId());
			info.setAccountName(a.getAccountName());
			info.setAccountNumber(a.getAccountNumber());
			info.setAccountStatus(a.getAccountStatus());
			infoList.add(info);
		});
		logger.info("Retrived AccountInfoList, Size {}",infoList.size());
		return infoList;
	}

	@Override
	public List<AccountInfo> getAccountInfoListByDate(Date date) {
		List<AccountInfoEntity> actinfoList = accountInfoRepo.findByGreateThanDate(date);
		List<AccountInfo> infoList = new ArrayList<>();
		actinfoList.forEach(a ->{
			AccountInfo info = new AccountInfo();
			info.setId(a.getId());
			info.setAccountName(a.getAccountName());
			info.setAccountNumber(a.getAccountNumber());
			info.setAccountStatus(a.getAccountStatus());
			infoList.add(info);
		});
		logger.info("Retrived AccountInfoList based on date {}, Size {}",date,infoList.size());
		return infoList;
	}

	@Override
	public AccountInfo openNewAccount(OpenAccountInfo openAccountInfo) {
		AccountInfoEntity accountInfoEntity = new AccountInfoEntity();
		
		String accountName = AccountUtil.getAccountName(openAccountInfo);
		String accountNumber = fetchNewAccountNumber(openAccountInfo);
		
		BeanUtils.copyProperties(openAccountInfo, accountInfoEntity);
		accountInfoEntity.setAccountName(accountName);
		accountInfoEntity.setAccountNumber(accountNumber);
		accountInfoEntity.setAccountStatus(StatusEnum.inactive.getStatus());
		logger.info("Account Info Entity for account openning {}", JsonUtil.toJson(accountInfoEntity));
		AccountInfoEntity savedEntity = accountInfoRepo.save(accountInfoEntity);
		
		AccountInfo returnSavedInfo = new AccountInfo();
		returnSavedInfo.setAccId(String.valueOf(savedEntity.getId()));
		returnSavedInfo.setAccountName(savedEntity.getAccountName());
		returnSavedInfo.setAccountNumber(savedEntity.getAccountNumber());
		returnSavedInfo.setAccountStatus(savedEntity.getAccountStatus());
		return returnSavedInfo;
	}

	@Override
	public String fetchNewAccountNumber(OpenAccountInfo openAccountInfo) {
		String currentTime = String.valueOf(new Date().getTime());
		String finalnewAccountNo = null;
		logger.info("Creating new Account Number for {}",openAccountInfo);
		if(null!=openAccountInfo) {
			String cityCode = BankMapUtil.getBranchCodeByBankName(openAccountInfo.getCity());
			String stateCode = BankMapUtil.getBankCodeByBankName(openAccountInfo.getState());
			finalnewAccountNo = stateCode.concat(cityCode).concat(currentTime);
			logger.info("Created new Account Number for {} is {}", openAccountInfo.getFirstName(), finalnewAccountNo);
			
		}
		return finalnewAccountNo;
	}

	@Override
	public List<AccountInfo> getAccountInfoListByDateAndStatus(Date days, String status) {
		List<AccountInfoEntity> infoActList = accountInfoRepo.findByGreateThanDateAndStatus(days, status);
		List<AccountInfo> infoList = new ArrayList<>();
		
		infoActList.forEach(act ->{
			AccountInfo info = new AccountInfo();
			BeanUtils.copyProperties(act, info);
			act.setAccountStatus(StatusEnum.reject.getStatus());
			act.setModifiedDt(new Date());
			act.setModifiedBy(StatusEnum.system.getStatus());
			infoList.add(info);
		});
		
		accountInfoRepo.saveAll(infoActList);
		
		logger.info("Size >> {}",infoActList.size());
		infoList.forEach(System.out::println);
		return infoList;
	}
	
	@Override
	public List<AccountInfo> updateAccountInfoListStatusByDate(Date days, String findingStatus, String updatingStatus) {
		List<AccountInfoEntity> dbInfoActList = accountInfoRepo.findByGreateThanDateAndStatus(days, findingStatus);
		List<AccountInfo> infoList = new ArrayList<>();
		dbInfoActList.forEach(act ->{
			act.setAccountStatus(updatingStatus);
			act.setModifiedDt(new Date());
			act.setModifiedBy(StatusEnum.system.getStatus());
		});
		List<AccountInfoEntity> updatedInfoActList = accountInfoRepo.saveAll(dbInfoActList);
		logger.info("Updated Size >> {}",updatedInfoActList.size());
		updatedInfoActList.forEach(updateAct ->{
			logger.info("Updated Status {} for accountNumber {}",updatingStatus,updateAct.getAccountNumber());
			AccountInfo info = new AccountInfo();
			BeanUtils.copyProperties(updateAct, info);
			infoList.add(info);
		});
		return infoList;
	}
	
}
