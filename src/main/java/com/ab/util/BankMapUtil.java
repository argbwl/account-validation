package com.ab.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankMapUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(BankMapUtil.class);
	
	private static Map<String, String> bankMap = new HashMap<>();
	private static Map<String, String> branchMap = new HashMap<>();
	
	static {
		//BankMap
		bankMap.put("TS", "923");
		bankMap.put("UP", "924");
		bankMap.put("AP", "925");
		bankMap.put("MP", "926");
		bankMap.put("GZ", "927");
		bankMap.put("MH", "927");
		
		//BranchMap
		branchMap.put("HYD", "390");
		branchMap.put("LKO", "393");
		branchMap.put("AMH", "203");
		branchMap.put("PYG", "202");
		branchMap.put("MUM", "748");
		branchMap.put("VNS", "848");
		branchMap.put("DEL", "394");
		
		logger.info("Loaded static data for bank and branch size if bank, branch is {} {}",bankMap.size(), branchMap.size());
	}
	
	
	public static String getBankCodeByBankName(String bankName) {
		logger.info("BankName {} ",bankName);
		return bankMap.getOrDefault(bankName, "111");
	}
	
	public static String getBranchCodeByBankName(String branchName) {
		logger.info("BranchName {}" ,branchName);
		return branchMap.getOrDefault(branchName, "999");
	}

}
