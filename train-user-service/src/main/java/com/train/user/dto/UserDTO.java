package com.train.user.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 4906196889834127621L;

	private Long userId;
	
	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String userSecurityId;

	private String encryptedPassword;

}
