package com.train.ticket.booking.service;

import com.train.ticket.booking.exception.TicketBookingBusinessException;
import com.train.ticket.booking.model.LastTicketBookingHistory;

public interface TicketBookingService {

	LastTicketBookingHistory getLastTicketBookingHistory(Long userId) throws TicketBookingBusinessException;

}
