package com.train.ticket.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.train.ticket.booking.exception.TicketBookingBusinessException;
import com.train.ticket.booking.model.LastTicketBookingHistory;
import com.train.ticket.booking.service.TicketBookingService;

@RestController
@RequestMapping("/ticket")
public class TicketBookingController {

	@Autowired
	private TicketBookingService ticketBookingService;

	@GetMapping
	public ResponseEntity<String> getAppStatus() {
		return new ResponseEntity<String>("Ticket Booking app is up and running", HttpStatus.OK);
	}

	@GetMapping("/view-last-booking/{userId}")
	public ResponseEntity<LastTicketBookingHistory> getLastTicketBookingHistory(@PathVariable Long userId)
			throws TicketBookingBusinessException {
		LastTicketBookingHistory lastTicketBookingHistory = ticketBookingService.getLastTicketBookingHistory(userId);
		return new ResponseEntity<LastTicketBookingHistory>(lastTicketBookingHistory, HttpStatus.OK);
	}
}
