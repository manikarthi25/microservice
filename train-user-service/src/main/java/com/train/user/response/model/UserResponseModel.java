package com.train.user.response.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseModel {

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String userId;

}
