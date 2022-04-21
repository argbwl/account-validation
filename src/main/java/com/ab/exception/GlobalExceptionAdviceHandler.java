package com.ab.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ab.constant.AccountValidationConstant;
import com.ab.pojo.StaticDataInfo;
import com.ab.service.IStaticDataService;

@RestControllerAdvice
public class GlobalExceptionAdviceHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private IStaticDataService staticDataService;
	
//	@ExceptionHandler(AccountValidationException.class)
//	public final ResponseEntity<Object> handleAccountValidationException(AccountValidationException ex){
//		return new ResponseEntity<>(prepareErrorJSON(ex, ex.getHttpStatus()), ex.getHttpStatus());
//	}
	
	@ExceptionHandler(AccountValidationException.class)
	public final ResponseEntity<APIException> handleAccountValidationException(AccountValidationException ex){
		StaticDataInfo info = staticDataService.retriveDataByKeyNameParam(AccountValidationConstant.CODE_LOWER_CASE, ex.getErrorCode());
		APIException apiException = new APIException();
		apiException.setDetails(ex.getDetails());
		apiException.setMessage(ex.getMessage());
		apiException.setStatus(ex.getHttpStatus());
		if(null!=info) {
			apiException.setErrorCode(info.getKeyParam());
			apiException.setErrorDesc(info.getKeyValue());
		}
		return new ResponseEntity<>(apiException, ex.getHttpStatus());
	}
	
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<APIException> handleUnknownException(Exception ex){
		StaticDataInfo info = staticDataService.retriveDataByKeyNameParam(AccountValidationConstant.CODE_LOWER_CASE, AccountValidationConstant.ERROR_2003);
		APIException apiException = new APIException();
		System.out.println(ex.getMessage());
		if(ex.getMessage().contains("ConstraintViolationException")) {
			apiException.setMessage("DB Process Issue");
		}else {
			apiException.setMessage("Internal System Error");
		}
		if(null!=info) {
			apiException.setErrorCode(info.getKeyParam());
			apiException.setErrorDesc(info.getKeyValue());
		}
		return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->{
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
	}
}
