package com.train.ticket.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PassengerDTO {

	private Long id;

	private Long ticketBookingId;

	private String firstName;

	private String lastName;

	private Integer age;

	private String gender;

	private String govtIdProff;

	private TicketBookingHistoryDTO ticketBookingHistoryDTO;

}
