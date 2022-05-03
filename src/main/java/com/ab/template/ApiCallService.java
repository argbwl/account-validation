package com.ab.template;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.ab.constant.AccountValidationConstant;
import com.ab.entity.UrlConfigEntity;
import com.ab.exception.ExternalSystemException;
import com.ab.logger.AVLogger;
import com.ab.repo.UrlConfigRepo;

@Component
public class ApiCallService {
	
	private static AVLogger avLogger = AVLogger.getLogger(ApiCallService.class);
	
	@Autowired	
	private RestTemplate restTemplate;
	
	@Autowired
	private UrlConfigRepo urlConfigRepo;

	public ResponseEntity<Object> callExtrenalAPI(String uri, HttpMethod reqType, Object jsonMessage) throws ExternalSystemException {
		String url = null;
		ResponseEntity<Object> response =null;
		if(StringUtils.isBlank(uri) || reqType==null) {
			avLogger.warn("URL [{}] or ReqType [{}] is blank ", uri, reqType);
			return null;
		}
		UrlConfigEntity urlEntity = urlConfigRepo.findByUri(uri);
		if(urlEntity!=null) {
			url = urlEntity.getUrl();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(jsonMessage, headers);
		try {
			response = restTemplate.exchange(url, reqType, httpEntity, Object.class);
			if(HttpStatus.OK.equals(response.getStatusCode())){
				avLogger.info("Response Received for URL [{}]:: Status [{}] and Response: [{}]",url,response.getStatusCode(), response.getBody());
			}else {
				avLogger.error("Failed Received Response for URL [{}]:: Status [{}]",url,response.getStatusCode());
				response =null;
				//throw exception 
			}
		}catch(ResourceAccessException e) {
			avLogger.error("Failed to connect for URL [{}]",url);
			throw new ExternalSystemException(uri, "Unable to Call API", HttpStatus.SERVICE_UNAVAILABLE, AccountValidationConstant.ERROR_4012);
		}
		return response;
	}
	
	
}
