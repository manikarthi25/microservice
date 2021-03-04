package com.train.ticket.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketBookingController {

	@GetMapping
	public ResponseEntity<String> getAppStatus() {
		return new ResponseEntity<String>("Ticket Booking app is up and running", HttpStatus.OK);
	}

}
