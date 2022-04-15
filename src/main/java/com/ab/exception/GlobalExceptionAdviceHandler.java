package com.ab.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ab.constant.AccountValidationConstant;
import com.ab.pojo.StaticDataInfo;
import com.ab.service.IStaticDataService;

@ControllerAdvice
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
	

}
