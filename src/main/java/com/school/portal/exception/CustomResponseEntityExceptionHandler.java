package com.school.portal.exception;

import java.util.NoSuchElementException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = {NoSuchElementException.class})
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request){		
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(value = EmptyResultDataAccessException.class)
	protected ResponseEntity<Object> handleConflict(EmptyResultDataAccessException ex, WebRequest request){
		String message = ex.getMessage();
		message = message.replaceAll("class|com|school|portal|model", "").replace(".", "");
		return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
}
