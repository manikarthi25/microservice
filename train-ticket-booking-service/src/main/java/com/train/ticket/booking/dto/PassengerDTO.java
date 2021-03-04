package com.train.ticket.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {
	
	private Long id;

	private Long ticketBookingId;

	private String firstName;

	private String lastName;

	private Integer age;

	private String gender;

	private String govtIdProff;

}
