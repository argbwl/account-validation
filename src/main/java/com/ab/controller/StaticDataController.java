package com.ab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ab.exception.AccountValidationException;
import com.ab.logger.AVLogger;
import com.ab.pojo.StaticDataInfo;
import com.ab.service.IStaticDataService;

@RestController
@RequestMapping("sdc")
public class StaticDataController {
	
	private AVLogger sdcLogger = AVLogger.getLogger(StaticDataController.class);
	
	@Autowired
	private IStaticDataService staticDataService;
	
	@GetMapping("retriveStaticDataList")
	public ResponseEntity<List<StaticDataInfo>> retriveStaticDataList() throws AccountValidationException{
		sdcLogger.info("Received Request to retrive Static Data List");
		List<StaticDataInfo> dataInfos = staticDataService.retrivedStaticDataInfoList();
		if(!dataInfos.isEmpty()) {
			return new ResponseEntity<>(dataInfos,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/getStaticData")
	public ResponseEntity<StaticDataInfo> getStaticDataByNameParam(@RequestBody StaticDataInfo staticDataInfo){
		try {
			sdcLogger.info("got request to fetch data for {} and {}",staticDataInfo.getKeyName(), staticDataInfo.getKeyParam());
			StaticDataInfo dataInfo = staticDataService.retriveDataByKeyNameParam(staticDataInfo.getKeyName(), staticDataInfo.getKeyParam());
			if(null!=dataInfo && 0!=dataInfo.getId()) {
				return new ResponseEntity<StaticDataInfo>(dataInfo, HttpStatus.FOUND);
			}
			return new ResponseEntity<StaticDataInfo>(dataInfo, HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			sdcLogger.error("Error Occured While Retriveing Data {} ", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occured While Retriveing Data");
		}
	}
	
	@PostMapping("/staticDataEntry-v1")
	public ResponseEntity<StaticDataInfo> saveNewStaticDataEntry(@RequestBody StaticDataInfo staticDataInfo) throws AccountValidationException{
		sdcLogger.info("got request to save new data for {} and {}",staticDataInfo.getKeyName(), staticDataInfo.getKeyParam());
		StaticDataInfo dataInfo = staticDataService.saveNewStaticDataByNameAndParam(staticDataInfo);
		if(null!=dataInfo && 0!=dataInfo.getId()) {
			return new ResponseEntity<StaticDataInfo>(dataInfo, HttpStatus.CREATED);
		}
		return new ResponseEntity<StaticDataInfo>(dataInfo, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/clear-all-cache")
	public ResponseEntity<String> clearCache() {
		String resp = staticDataService.clearAllCache();
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	
	

}
