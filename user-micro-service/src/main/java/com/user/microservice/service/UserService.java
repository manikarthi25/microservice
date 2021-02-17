package com.user.microservice.service;

import java.util.List;

import com.user.microservice.dto.User;

public interface UserService {

	List<User> getAllUsers();

	User getUserByUserId(Long userId);

	User addNewUser(User user);

	User updateUserByUserId(User user);

	List<User> deleteUserByUserId(Long userId);

}
