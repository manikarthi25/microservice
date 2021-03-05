package com.train.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.train.user.dto.UserDTO;
import com.train.user.request.model.UserRequestModel;
import com.train.user.response.model.UserResponseModel;

public interface UserService extends UserDetailsService {

	UserResponseModel addUser(UserRequestModel userRequestModel);

	UserDTO getUserDetailsByEmail(String email);

	UserDTO getUserDetailsByUserId(Long userId);

}
