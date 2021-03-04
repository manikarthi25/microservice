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
@Table(name = "train")
public class TrainEO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long trainNumber;

	private String departurePlace;

	private String arrivalPlace;

	private String viaPlace;

	private Double ticketFare;

}
