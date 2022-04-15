package com.ab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ab.util.DateUtil;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableIntegration
public class AccountValidationApplication implements CommandLineRunner{
	
	@Value("${server.port}")
	private String port;
	
	public static void main(String[] args) {
		SpringApplication.run(AccountValidationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("\n["+DateUtil.getCurrentDateTime()+"] AccountValidationApplication Started on Port "+port);
		
	}

}
