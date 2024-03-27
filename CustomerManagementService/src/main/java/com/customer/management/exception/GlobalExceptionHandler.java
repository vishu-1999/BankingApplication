package com.customer.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

@ExceptionHandler(CustomerNotFoundException.class)
public ResponseEntity<ErrorMessage> handleCustomerNotFoundException(CustomerNotFoundException ex,WebRequest req){
	ErrorMessage errorMsg = new ErrorMessage(HttpStatus.NOT_FOUND,ex.getMessage());
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
}
}
