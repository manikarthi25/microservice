package com.train.ticket.booking.service;

import com.train.ticket.booking.exception.TicketBookingBusinessException;
import com.train.ticket.booking.model.LastTicketBookingHistory;
import com.train.ticket.booking.model.TicketBooking;
import com.train.ticket.booking.model.UserReponse;

public interface TicketBookingService {

	LastTicketBookingHistory getLastTicketBookingHistory(Long userId) throws TicketBookingBusinessException;

	LastTicketBookingHistory bookTrainTicket(TicketBooking ticketBooking) throws TicketBookingBusinessException;
	
	UserReponse getUserDetailsByUserId(Long userId);

}
