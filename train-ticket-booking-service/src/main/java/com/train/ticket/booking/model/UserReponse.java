package com.train.ticket.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserReponse {

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private Long userId;
	
	private String message;

}
