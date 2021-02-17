package com.springboot.training.exception;

import javax.naming.directory.InvalidAttributesException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandling.class);

	@ExceptionHandler(value = InvalidAttributesException.class)
	public ResponseEntity<Object> catchInvalidAttributeException(InvalidAttributesException ex) {
		LOGGER.error("Invalid Attribute Exception :", ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
