package com.train.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.train.user.dto.UserDTO;

public interface UserService extends UserDetailsService {

	UserDTO addUser(UserDTO employeeDTO);

	UserDTO getUserDetailsByEmail(String email);

	UserDTO getUserDetailsByUserId(String userId);

}
