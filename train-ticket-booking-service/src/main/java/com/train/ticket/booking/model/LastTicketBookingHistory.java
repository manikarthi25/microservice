package com.train.ticket.booking.model;

import java.time.LocalDateTime;
import java.util.List;

import com.train.ticket.booking.dto.PassengerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastTicketBookingHistory {

	private Long ticketBookingId;

	private Long userId;

	private Long trainNumber;

	private Double totalTicketFare;

	private Integer ticketCount;

	private LocalDateTime bookingDateTime;

	private LocalDateTime travelDateTime;

	private String departurePlace;

	private String arrivalPlace;

	private String viaPlace;

	private List<PassengerDTO> passengerList;

}
