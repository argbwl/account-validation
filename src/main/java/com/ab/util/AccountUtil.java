package com.ab.util;

import org.apache.commons.lang3.StringUtils;

import com.ab.pojo.AccountInfo;
import com.ab.ui.pojo.OpenAccountInfo;

public class AccountUtil {
	
	public static boolean validateAccountUpload(AccountInfo accountInfo) {
		if(null!=accountInfo && StringUtils.isEmpty(accountInfo.getAccountNumber()) && StringUtils.isBlank(accountInfo.getAccountName())) {
			return false;
		}else {
			return true;
		}
	}

	public static String getAccountName(OpenAccountInfo openAccountInfo) {
		String name = "";
		if(null!=openAccountInfo) {
			if(StringUtils.isNotBlank(openAccountInfo.getFirstName())) name = name.concat(openAccountInfo.getFirstName());
			if(StringUtils.isNotBlank(openAccountInfo.getMiddleName())) name = name.concat(" "+openAccountInfo.getMiddleName());
			if(StringUtils.isNotBlank(openAccountInfo.getLastName())) name = name.concat(" "+openAccountInfo.getLastName());
		}
		return name;
	}
	
}
