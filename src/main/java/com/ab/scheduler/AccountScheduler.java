package com.ab.scheduler;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ab.enumpkg.StatusEnum;
import com.ab.pojo.AccountInfo;
import com.ab.service.IAccountInfoService;
import com.ab.service.IStaticDataService;
import com.ab.util.DateUtil;

@Component
public class AccountScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountScheduler.class);

	@Autowired
	private IAccountInfoService accountInfoService;
	
	@Autowired 
	private IStaticDataService staticDataService;
	
	@Autowired
	private Environment env;
	
	@Scheduled(cron = "${cronExpression}")
	public void cronJobSch() {
		int days = Integer.parseInt(env.getProperty("accountRejectDaysInterval"));
		logger.info("Start Scheduler to Reject Account More Than {} Days", days);
		Date daysInterval = DateUtil.getLastDays(days);
		List<AccountInfo> updatedInfoList = accountInfoService.updateAccountInfoListStatusByDate(daysInterval, StatusEnum.inactive.getStatus(), StatusEnum.reject.getStatus());
		logger.info("End Scheduler to Reject Account More Than {} Days, total count updated is {}", days,updatedInfoList.size());
	}
	
	@Scheduled(cron = "${clearCacheCronExcpression}")
	public void cronJobSchClearCache() {
		logger.info("Start clearing cache");
		staticDataService.clearAllCache();
		logger.info("End clearing cache");
	}
	
}
