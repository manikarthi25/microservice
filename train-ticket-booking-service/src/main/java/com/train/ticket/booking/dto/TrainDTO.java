package com.train.ticket.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainDTO {

	private Long trainNumber;

	private String departurePlace;

	private String arrivalPlace;

	private String viaPlace;

	private Double ticketFare;

}
