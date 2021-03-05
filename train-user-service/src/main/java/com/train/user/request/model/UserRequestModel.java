package com.train.user.request.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestModel {

	@NotNull(message = "First Name can not bel null")
	private String firstName;

	@NotNull(message = "First Name can not bel null")
	private String lastName;

	@NotNull(message = "First Name can not bel null")
	@Email
	private String email;

	@NotNull(message = "First Name can not bel null")
	@Size(min = 3, max = 10)
	private String password;

}
