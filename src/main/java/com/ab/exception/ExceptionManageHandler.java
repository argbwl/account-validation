package com.ab.exception;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

@Configuration
public class ExceptionManageHandler {
	
	//Alternative approach of removing this field can be manage from application.properties as well
	//server.error.include-stacktrace=never
	@Bean
	public ErrorAttributes errorAttributes() {
		return new DefaultErrorAttributes() {
			@Override
			public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
				Map<String, Object> errorAtrMap = super.getErrorAttributes(webRequest, options);
//				errorAtrMap.forEach((k,v)->{
//					System.out.println("Map Value -> Key and Value : "+k+", "+v);
//				});
				errorAtrMap.remove("trace");
				errorAtrMap.remove("path");
				return errorAtrMap;
			}
		};
	}

}
