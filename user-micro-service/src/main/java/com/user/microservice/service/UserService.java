package com.user.microservice.service;

import java.util.List;

import com.user.microservice.dto.Order;
import com.user.microservice.dto.User;
import com.user.microservice.dto.UserOrder;

public interface UserService {

	List<User> getAllUsers();

	User getUserByUserId(Long userId);

	User addNewUser(User user);

	User updateUserByUserId(User user);

	List<User> deleteUserByUserId(Long userId);

	UserOrder getOrderByUserId(Long userId);

	Order updateOrderFromUser(Order order);

}
