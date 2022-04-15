package com.ab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ab.logger.AVLogger;

@RestController
@RequestMapping("hc")
public class HealthCheckController {
	
	private static final AVLogger logger = AVLogger.getLogger(HealthCheckController.class);
	
	@GetMapping("/health-check")
	public ResponseEntity<String> getHealthCheck(){
		logger.info("Health check requested");
		return new ResponseEntity<String>("Application is Running",HttpStatus.OK);
	}

}
