package com.train.ticket.booking.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketBookingHistoryDTO {
	
	private Long ticketBookingId;
	
	private Long userId;
	
	private Long trainNumber;
	
	private String pnrNumber;
	
	private String departurePlace;
	
	private String arrivalPlace;
	
	private Double ticketFare;
	
	private LocalDateTime bookingDateTime;
	
	private LocalDateTime travelDateTime;
	
	

}
