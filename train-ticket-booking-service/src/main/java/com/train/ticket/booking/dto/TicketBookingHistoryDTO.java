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

	private Double totalTicketFare;

	private Integer ticketCount;

	private LocalDateTime bookingDateTime;

	private LocalDateTime travelDateTime;

	private TrainDTO trainDTO;

}
