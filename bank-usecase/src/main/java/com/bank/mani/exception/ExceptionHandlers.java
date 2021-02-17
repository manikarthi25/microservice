package com.bank.mani.exception;

import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> catchGenericException(Exception ex) {
		LOGGER.error("Exception :", ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = BankBusinessException.class)
	public ResponseEntity<Object> catchBusinessException(BankBusinessException ex) {
		LOGGER.error("Business Exception :", ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<Object> catchIllegalArgumentException(IllegalArgumentException ex) {
		LOGGER.error("IllegalArgumentException Exception :", ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = DateTimeParseException.class)
	public ResponseEntity<Object> catchDateTimeParseException(DateTimeParseException ex) {
		LOGGER.error("DateTimeParseException Exception :", ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
