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
public class TicketBooking {

	private Long userId;

	private Long trainNumber;

	private Integer ticketCount;

	private LocalDateTime travelDateTime;

	private String departurePlace;

	private String arrivalPlace;

	private List<PassengerDTO> passengerList;

}
