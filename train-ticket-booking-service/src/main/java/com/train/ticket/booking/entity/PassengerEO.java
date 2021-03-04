package com.train.ticket.booking.entity;

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
@Table(name = "passenger")
public class PassengerEO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long ticketBookingId;

	private String firstName;

	private String lastName;

	private Integer age;

	private String gender;

	private String govtIdProff;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ticketBookingId", referencedColumnName = "ticketBookingId", insertable = false, updatable = false)
	private TicketBookingHistoryEO ticketBookingHistoryEO;

}
