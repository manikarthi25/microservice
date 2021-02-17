package com.bank.mani.exception;

public class BankBusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public BankBusinessException() {
		super();
	}

	public BankBusinessException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public BankBusinessException(String errorMessage, Throwable ex) {
		super(errorMessage, ex);
		this.errorMessage = errorMessage;
	}

}
