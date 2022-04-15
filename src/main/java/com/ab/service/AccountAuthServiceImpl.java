package com.ab.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ab.constant.AccountValidationConstant;
import com.ab.entity.AccountAuthEntity;
import com.ab.enumpkg.StatusEnum;
import com.ab.exception.AccountValidationException;
import com.ab.pojo.AccountAuth;
import com.ab.pojo.AccountAuthResponse;
import com.ab.pojo.AccountInfo;
import com.ab.repo.AccountAuthRepo;
import com.ab.util.JsonUtil;

@Service
@Transactional
public class AccountAuthServiceImpl implements IAccountAuthService {

	private static final Logger logger = LoggerFactory.getLogger(AccountAuthServiceImpl.class);
	
	@Autowired
	private AccountAuthRepo accountAuthRepo;
	
	@Autowired
	private IAccountInfoService accountInfoService;

	@Override
	public AccountAuth authorizeAccount(AccountAuth accountAuth) throws AccountValidationException {
		logger.info("Start Authorization in service");
		AccountInfo info = new AccountInfo();
		AccountAuth auth = new AccountAuth();
		info.setAccountName(accountAuth.getAccountName());
		info.setAccountNumber(accountAuth.getAccountNumber());
		info.setCheckStatus(StatusEnum.inactive.getStatus());
		AccountInfo accInfoDB = accountInfoService.getAccountInfoByAccNumber(info);
		info.setId(accInfoDB.getId());
		logger.info("Data Retreived from DB with ID {} ",accInfoDB.getId());
		if(null!=accInfoDB && 0!=accInfoDB.getId()) {
			AccountAuthEntity accountAuthEntity = new AccountAuthEntity();
			accountAuthEntity.setAccId(accInfoDB.getId());
			accountAuthEntity.setAccName(accInfoDB.getAccountName());
			accountAuthEntity.setAccNumber(accInfoDB.getAccountNumber());
			accountAuthEntity.setStatus(StatusEnum.pending.getStatus());
			accountAuthEntity.setAuthorizedBy(accountAuth.getApprovedBy());
			AccountAuthEntity accountAuthEntityDB = accountAuthRepo.save(accountAuthEntity);
			auth.setAccountName(accountAuthEntityDB.getAccName());
			auth.setAccountNumber(accountAuthEntityDB.getAccNumber());
			auth.setStatus(StatusEnum.pending.getStatus());
			auth.setAccId(accountAuthEntityDB.getAccId());
			accountInfoService.updateAcountInfoStatusById(info.getId(), StatusEnum.authpend.getStatus());
		}else {
			logger.info("No Records Found in DB");
			throw new AccountValidationException("No Valid Account Found with Account Number "+accountAuth.getAccountNumber(), "No Data Found", HttpStatus.NOT_FOUND, AccountValidationConstant.ERROR_3015);
		}
		logger.info("End Authorization in service");
		return auth;
	}

	@Override
	public AccountAuth approveAccount(AccountAuth accAuth) {
		logger.info("Start Approving account");
		AccountAuth auth = new AccountAuth();
		AccountAuthEntity accountAuthEntity = accountAuthRepo.findByAccNameAndAccNumber(accAuth.getAccountName(), accAuth.getAccountNumber());
		if(null!=accountAuthEntity) {
			if(accAuth.getApprovedBy().equals(accountAuthEntity.getApprovedBy())) {
				logger.error("can not be approve by same user {} ",accAuth.getApprovedBy());
				return auth;
			}
			if(StatusEnum.approved.getStatus().equals(accountAuthEntity.getStatus()) || StatusEnum.reject.getStatus().equals(accountAuthEntity.getStatus())) {
				logger.error("account already approved or rejected");
				return auth;
			}
			accountAuthEntity.setStatus(StatusEnum.approved.getStatus());
			accountAuthEntity.setModifiedDt(new Date());
			accountAuthEntity.setApprovedBy(accAuth.getApprovedBy());
			//Updating Same record with modified fields
			AccountAuthEntity accAuthUpdated = accountAuthRepo.save(accountAuthEntity);
			auth.setAccId(accAuthUpdated.getAccId());
			auth.setStatus(StatusEnum.active.getStatus());
			accountInfoService.updateAcountInfoStatusById(accAuthUpdated.getAccId(),StatusEnum.active.getStatus());
			logger.info("Account verified");
		}
		logger.info("End Approving account");
		return auth;
	}
	
	@Override
	public AccountAuthResponse rejectAccount(AccountAuth accountAuth) {
		logger.info("Start Rejection in service");
		AccountInfo info = new AccountInfo();
		AccountAuthResponse auth = new AccountAuthResponse();
		info.setAccountName(accountAuth.getAccountName());
		info.setAccountNumber(accountAuth.getAccountNumber());
		info.setCheckStatus(StatusEnum.authpend.getStatus());
		AccountInfo accInfoDB = accountInfoService.getAccountInfoByAccNumber(info);
		info.setId(accInfoDB.getId());
		logger.info("Data Retreived from DB with ID {} , Json {}",accInfoDB.getId(), JsonUtil.toJson(accInfoDB));
		if(null!=accInfoDB && 0!=accInfoDB.getId()) {
			AccountAuthEntity accountAuthEntity = accountAuthRepo.findByAccNumber(accInfoDB.getAccountNumber());
			if(null!=accountAuthEntity) {
				accountAuthEntity.setStatus(StatusEnum.reject.getStatus());
				accountAuthEntity.setApprovedBy(accountAuth.getApprovedBy());
				AccountAuthEntity accountAuthEntityDB = accountAuthRepo.save(accountAuthEntity);
				auth.setAccountName(accountAuthEntityDB.getAccName());
				auth.setAccountNumber(accountAuthEntityDB.getAccNumber());
				auth.setStatus(StatusEnum.reject.getStatus());
				auth.setAccId(accountAuthEntityDB.getAccId());
				auth.setApprovedBy(accountAuth.getApprovedBy());
				accountInfoService.updateAcountInfoStatusById(info.getId(), StatusEnum.reject.getStatus());
			}else {
				logger.info("No Records Found in DB");
			}
		}else {
			logger.info("No Records Found in DB");
		}
		logger.info("End Rejection in service");
		return auth;
	}

}
