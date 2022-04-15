package com.ab.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ab.constant.AccountValidationConstant;
import com.ab.entity.StaticDataEntity;
import com.ab.enumpkg.StatusEnum;
import com.ab.exception.AccountValidationException;
import com.ab.exception.DBException;
import com.ab.logger.AVLogger;
import com.ab.pojo.StaticDataInfo;
import com.ab.repo.StaticDataRepo;
import com.ab.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class StaticDataServiceImpl implements IStaticDataService {
	
	private AVLogger logger = AVLogger.getLogger(StaticDataServiceImpl.class);
	
	@Autowired
	private StaticDataRepo staticDataRepo;

	@Override
	public StaticDataInfo retriveDataByKeyNameParamAndInd(String keyName, String keyParam, boolean keyInd) throws DBException{
		logger.info("Start Retriving Static Data from DB");
		StaticDataEntity staticDataEntity = staticDataRepo.findByKeyNameAndKeyParamAndKeyInd(keyName, keyParam, keyInd);
		StaticDataInfo info = new StaticDataInfo();
		if(null!=staticDataEntity) {
			BeanUtils.copyProperties(staticDataEntity, info);
			logger.info("End Retriving Static Data from DB, value: {} ",info.getKeyValue());
		}else {
			logger.info("End Retriving Static Data from DB");
		}
		return info;
	}

	@Override
	public StaticDataInfo createNewStaticDataEntry(StaticDataInfo staticDataInfo) {
		StaticDataEntity entity = new StaticDataEntity();
		StaticDataInfo newInfo = new StaticDataInfo();
		if(null!=staticDataInfo) {
			BeanUtils.copyProperties(staticDataInfo, entity);
			entity.setKeyInd(true);
			entity.setModifiedBy(StatusEnum.system.getStatus());
			entity.setKeyVersion(0);
			StaticDataEntity dbNewEntity = staticDataRepo.save(entity);
			BeanUtils.copyProperties(dbNewEntity, newInfo);
			logger.info("Created new Entry for static Data with ID {}",newInfo.getId());
		}
		return newInfo;
	}

	@Override
	@Cacheable(cacheNames = "static_data", key = "#keyName+#keyParam")
	public StaticDataInfo retriveDataByKeyNameParam(String keyName, String keyParam){
		logger.info("Start Retriving Static Data for key and param {} and {}",keyName, keyParam);
		try {
			StaticDataInfo staticDataInfo = retriveDataByKeyNameParamAndInd(keyName, keyParam, true);
			logger.info("Retrived Static Data for key and param {} and {} with Value {}",keyName, keyParam, staticDataInfo.getKeyValue());
			return staticDataInfo;
		}catch (DBException e) {
			logger.error("Error Occured while featching records from DB {} ", e.getMessage());
			return new StaticDataInfo();
		}catch (Exception e) {
			logger.error("Error Unknow Occured while featching records from DB {} ", e.getMessage());
			return new StaticDataInfo();
		}
	}

	@Override
	public List<StaticDataInfo> retrivedStaticDataInfoList() throws AccountValidationException {
		logger.info("Start retriving list of Static Data");
		List<StaticDataEntity> staticDataEntityDBList = new ArrayList<>();
		List<StaticDataInfo> staticDataInfoList = new ArrayList<>();
		try {
			staticDataEntityDBList = staticDataRepo.findAll();
			staticDataEntityDBList.forEach(dbEntity->{
				StaticDataInfo info = new StaticDataInfo();
				BeanUtils.copyProperties(dbEntity, info);
				staticDataInfoList.add(info);
			});
			logger.info("End retriving list of Static Data, Retrived Size id [{}] ",staticDataInfoList.size());
			return staticDataInfoList;
		}catch (DBException e) {
			logger.error("Error Occured while featching records from DB {} ", e.getMessage());
			return staticDataInfoList;
		}catch (Exception e) {
			logger.error("Error Unknow Occured while featching records from DB {} ", e.getMessage());
			throw new AccountValidationException("Error Occured while retriving Data",null,HttpStatus.INTERNAL_SERVER_ERROR,AccountValidationConstant.ERROR_2003);
		}
	}

	@Override
	//@CachePut(cacheNames = "static_data", key = "#staticDataInfo.keyName+#staticDataInfo.keyParam")
	@CacheEvict(cacheNames = "static_data", key = "#staticDataInfo.keyName+#staticDataInfo.keyParam")
	public StaticDataInfo saveNewStaticDataByNameAndParam(StaticDataInfo staticDataInfo) throws AccountValidationException {
		String keyName = staticDataInfo.getKeyName();
		String keyParam = staticDataInfo.getKeyParam();
		String keyValue = staticDataInfo.getKeyValue();
		logger.info("Start retriving static data list by Name {} and Param {}",keyName, keyParam);
		String remarks = null;
		StaticDataInfo info = new StaticDataInfo();
		if(StringUtils.isBlank(keyParam) || StringUtils.isBlank(keyName) || StringUtils.isBlank(keyValue)) {
			StaticDataInfo errorInfo = new StaticDataInfo();
			BeanUtils.copyProperties(staticDataInfo, errorInfo);
			errorInfo.setRemarks("Missing required fields");
			throw new AccountValidationException("Request form is not valid",null,HttpStatus.BAD_REQUEST,AccountValidationConstant.ERROR_3001);
		}
		List<StaticDataEntity> dbStaticDateEntites = staticDataRepo.findByKeyNameAndKeyParam(keyName,keyParam);
		if(!dbStaticDateEntites.isEmpty()) {
			logger.info("Data found in DB with this key value combination, size {}",dbStaticDateEntites.size());
			String lastDB0VersionValue = null;
			Optional<StaticDataEntity> optDbEntity = dbStaticDateEntites.stream().filter(a -> a.getKeyVersion()==0).findAny();
			if(optDbEntity.isPresent()) {
				lastDB0VersionValue=optDbEntity.get().getKeyValue();
				logger.info("version 0 value is {} and newValue is {}",lastDB0VersionValue,keyValue);
				if(keyValue.equals(lastDB0VersionValue)) {
					logger.error("Data is already Present with ID {}",optDbEntity.get().getId());
					StaticDataInfo errorInfo = new StaticDataInfo();
					BeanUtils.copyProperties(staticDataInfo, errorInfo);
					errorInfo.setRemarks("Data is already Present with ID "+optDbEntity.get().getId());
					throw new AccountValidationException("Data is already Present with ID "+optDbEntity.get().getId(),null,HttpStatus.UNPROCESSABLE_ENTITY,AccountValidationConstant.ERROR_3014);
				}
			}
			remarks="New Entry Updated, Last KeyValue is "+lastDB0VersionValue;
			logger.info(remarks);
			dbStaticDateEntites.forEach(dbEntity ->{
				if(dbEntity.getKeyVersion()==0) {
					dbEntity.setModifiedDt(DateUtil.getCurrentDateTime());
				}
				int currentVersion = dbEntity.getKeyVersion();
				logger.info("Current Version for ID {} is {}",dbEntity.getId(), dbEntity.getKeyVersion());
				dbEntity.setModifiedBy(StatusEnum.system.getStatus());
				dbEntity.setKeyInd(false);
				dbEntity.setKeyVersion(currentVersion+1);
			});
		}
		StaticDataEntity newEntity = new StaticDataEntity();
		newEntity.setKeyName(keyName);
		newEntity.setKeyParam(keyParam);
		newEntity.setKeyValue(keyValue);
		newEntity.setKeyInd(true);
		newEntity.setKeyVersion(0);
		newEntity.setModifiedBy(StatusEnum.insert.getStatus());
		dbStaticDateEntites.add(newEntity);
		staticDataRepo.saveAll(dbStaticDateEntites);
		BeanUtils.copyProperties(newEntity, info);
		info.setRemarks(remarks);
		return info;
	}

	@Override
	@CacheEvict(cacheNames = "static_data", allEntries = true)
	public String clearAllCache() {
		logger.info("All static_data cache cleared");
		return "Cleared All Cache releated to cache static_data";
	}

}
