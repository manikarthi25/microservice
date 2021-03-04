package com.train.ticket.booking.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	private Double totalTicketFare;
	
	private Integer ticketCount;

	private LocalDateTime bookingDateTime;

	private LocalDateTime travelDateTime;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "trainNumber", referencedColumnName = "trainNumber", insertable = false, updatable = false)
	private TrainEO trainEO;

}
