package com.user.microservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.user.microservice.dto.Order;
import com.user.microservice.dto.User;
import com.user.microservice.dto.UserOrder;
import com.user.microservice.entity.UserEO;
import com.user.microservice.feignclient.OrderClient;
import com.user.microservice.repo.UserRepo;
import com.user.microservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;

	@Autowired
	private OrderClient orderClient;

	@Autowired
	private UserRepo userRepo;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<>();
		List<UserEO> userEOList = userRepo.findAll();
		userEOList.forEach(userEO -> userList.add(mapToDTO(userEO)));
		return userList;
	}

	@Override
	public User getUserByUserId(Long userId) {
		Optional<UserEO> userEO = userRepo.findById(userId);
		if (userEO.isPresent()) {
			return mapToDTO(userEO.get());
		} else {
			return null;
		}
	}

	@Override
	public User addNewUser(User user) {
		UserEO userEO = userRepo.save(mapToEO(user));
		return mapToDTO(userEO);
	}

	@Override
	public User updateUserByUserId(User user) {
		User userResponse = getUserByUserId(user.getUserId());
		if (null != userResponse) {
			UserEO userEO = new UserEO();
			userEO.setUserId(user.getUserId());
			userEO.setUserName(user.getUserName());
			return mapToDTO(userRepo.saveAndFlush(userEO));
		}
		return null;
	}

	@Override
	public UserOrder getOrderByUserId(Long userId) {
		UserOrder userOrder = new UserOrder();
		User user = getUserByUserId(userId);

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("getorder");

		Order order = circuitBreaker.run(() -> orderClient.getOrderByUserId(userId).getBody(),
				Throwable -> getDefaultMessage(userId));
		if (ObjectUtils.isEmpty(order)) {
			userOrder.setMessage("Please try after some time");
		} else {
			userOrder.setMessage("Order service uo and running, Got response from order service");
			userOrder.setOrder(order);
			userOrder.setUser(user);
		}
		return userOrder;
	}

	public Order getDefaultMessage(Long userId) {
		return null;
	}

	@Override
	public Order updateOrderFromUser(Order order) {
		Order orderRes = orderClient.updateOrderByOrderId(order).getBody();
		return orderRes;
	}

	@Override
	public List<User> deleteUserByUserId(Long userId) {
		Optional<UserEO> userEO = userRepo.findById(userId);
		if (userEO.isPresent()) {
			userRepo.deleteById(userId);
			return getAllUsers();
		}
		return null;
	}

	private UserEO mapToEO(User user) {
		return modelMapper.map(user, UserEO.class);
	}

	private User mapToDTO(UserEO userEO) {
		return modelMapper.map(userEO, User.class);
	}

}
