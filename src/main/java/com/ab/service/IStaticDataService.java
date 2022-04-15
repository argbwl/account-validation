package com.ab.service;

import java.util.List;

import com.ab.exception.AccountValidationException;
import com.ab.exception.DBException;
import com.ab.pojo.StaticDataInfo;

public interface IStaticDataService {
	
	public StaticDataInfo retriveDataByKeyNameParamAndInd(String keyName, String keyParam, boolean keyInd) throws DBException;
	public StaticDataInfo retriveDataByKeyNameParam(String keyName, String keyParam);
	public StaticDataInfo createNewStaticDataEntry(StaticDataInfo staticDataInfo);
	public List<StaticDataInfo> retrivedStaticDataInfoList() throws AccountValidationException;
	public StaticDataInfo saveNewStaticDataByNameAndParam(StaticDataInfo staticDataInfo) throws AccountValidationException;
	public String clearAllCache();

}
