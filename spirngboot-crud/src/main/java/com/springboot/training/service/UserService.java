package com.springboot.training.service;

import java.util.List;

import com.springboot.training.dto.UserDTO;
import com.springboot.training.dto.UserNamesDTO;

public interface UserService {

	UserDTO addNewUser(UserDTO userDTO);

	List<UserDTO> getAllUser();

	List<UserNamesDTO> getUserFirstAndLastName();

	UserDTO getUserbyUserId(Long userId);

	UserDTO updateUserByUserId(UserDTO userDTO);

	List<UserDTO> deleteUserbyUserId(Long userId);

	UserDTO getUserbyFirstName(String userFirstName);

	List<UserDTO> getUsersUsingPagination(int pageBatchNumber, int pageSize);

	List<UserDTO> getUsersByFirstNameUsingPagination(String firstName, int pageBatchNumber, int pageSize);

}
