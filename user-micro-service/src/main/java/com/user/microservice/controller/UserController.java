package com.user.microservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.microservice.dto.Order;
import com.user.microservice.dto.User;
import com.user.microservice.dto.UserOrder;
import com.user.microservice.feignclient.OrderClient;
import com.user.microservice.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private OrderClient orderClient;

	@GetMapping("/getusers")
	public ResponseEntity<List<User>> getAllUsers() {
		return getUsersRespone(userService.getAllUsers());
	}

	@GetMapping("/search/{userId}")
	public ResponseEntity<User> getUserByUserId(@PathVariable Long userId) {
		return getUserRespone(userService.getUserByUserId(userId));
	}

	@PostMapping("/adduser")
	public ResponseEntity<User> addNewUser(@Valid @RequestBody User user) {
		return getUserRespone(userService.addNewUser(user));
	}

	@PutMapping("/updateuser/{userId}")
	public ResponseEntity<User> updateUserByUserId(@Valid @RequestBody User user) {
		return getUserRespone(userService.updateUserByUserId(user));
	}

	@DeleteMapping("/deleteuser/{userId}")
	public ResponseEntity<List<User>> deleteUserByUserId(@PathVariable Long userId) {
		return getUsersRespone(userService.deleteUserByUserId(userId));
	}

	@GetMapping("/getorder/{userId}")
	public ResponseEntity<UserOrder> getOrderByUserId(@PathVariable Long userId) {
		UserOrder userOrder = new UserOrder();
		User user = userService.getUserByUserId(userId);
		Order order = orderClient.getOrderByUserId(userId).getBody();
		userOrder.setOrder(order);
		userOrder.setUser(user);

		if (ObjectUtils.isNotEmpty(userOrder)) {
			return new ResponseEntity<UserOrder>(userOrder, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserOrder>(userOrder, HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping("/updateorder")
	public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order) {
		Order orderRes = orderClient.updateOrderByOrderId(order).getBody();
		if (ObjectUtils.isNotEmpty(orderRes)) {
			return new ResponseEntity<Order>(orderRes, HttpStatus.OK);
		} else {
			return new ResponseEntity<Order>(orderRes, HttpStatus.NO_CONTENT);
		}
	}

	private ResponseEntity<User> getUserRespone(User user) {
		if (ObjectUtils.isNotEmpty(user)) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
		}
	}

	private ResponseEntity<List<User>> getUsersRespone(List<User> userList) {
		if (!CollectionUtils.isEmpty(userList)) {
			return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<User>>(userList, HttpStatus.NO_CONTENT);
		}
	}

}
