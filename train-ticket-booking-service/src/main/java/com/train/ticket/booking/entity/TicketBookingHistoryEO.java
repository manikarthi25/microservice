package com.train.ticket.booking.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "ticket_booking_history")
public class TicketBookingHistoryEO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ticketBookingId;

	private Long userId;

	private Long trainNumber;

	private String pnrNumber;

	private String departurePlace;

	private String arrivalPlace;

}
