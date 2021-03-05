package com.train.user.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.train.user.dto.UserDTO;
import com.train.user.request.model.UserRequestModel;
import com.train.user.response.model.UserReponse;
import com.train.user.response.model.UserResponseModel;
import com.train.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private Environment environment;

	@Autowired
	private UserService userService;

	@GetMapping("/status")
	public String getUserAppStatus() {
		return "User App is working on port number" + environment.getProperty("local.server.port");
	}

	@PostMapping("/signup")
	public ResponseEntity<UserResponseModel> addUser(@RequestBody UserRequestModel userRequestModel) {

		UserResponseModel userResponseModel = userService.addUser(userRequestModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponseModel);

	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserReponse> getUserDetailsByUserId(@PathVariable Long userId) {

		UserDTO userDTO = userService.getUserDetailsByUserId(userId);
		UserReponse userReponse = new ModelMapper().map(userDTO, UserReponse.class);
		return ResponseEntity.status(HttpStatus.OK).body(userReponse);

	}
}
