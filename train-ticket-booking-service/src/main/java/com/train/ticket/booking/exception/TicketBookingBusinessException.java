package com.train.ticket.booking.exception;

public class TicketBookingBusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public TicketBookingBusinessException() {
		super();
	}

	public TicketBookingBusinessException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public TicketBookingBusinessException(String errorMessage, Throwable ex) {
		super(errorMessage, ex);
		this.errorMessage = errorMessage;
	}

}
