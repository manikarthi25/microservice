package com.springboot.training.controller;

import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.training.dto.UserDTO;
import com.springboot.training.dto.UserNamesDTO;
import com.springboot.training.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/status")
	public String getAppStatus() {
		return "user application up and running";
	}

	@PostMapping("/add")
	public ResponseEntity<UserDTO> addNewUser(@RequestBody UserDTO userDTO) throws InvalidAttributesException {
		if (null == userDTO.getFirstName()) {
			LOGGER.error("Invalid Attribute Exception");
			throw new InvalidAttributesException();
		}
		return getUser(userService.addNewUser(userDTO));

	}

	@GetMapping("/getall")
	public ResponseEntity<List<UserDTO>> getAllUser() {
		return getUsers(userService.getAllUser());
	}

	@GetMapping("/get/firstandlastname")
	public ResponseEntity<List<UserNamesDTO>> getUserFirstAndLastName() {
		List<UserNamesDTO> userNames = userService.getUserFirstAndLastName();
		if (null != userNames) {
			return new ResponseEntity<List<UserNamesDTO>>(userNames, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<UserNamesDTO>>(userNames, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/search/userid/{userId}")
	public ResponseEntity<UserDTO> getUserbyUserId(@PathVariable Long userId) {
		return getUser(userService.getUserbyUserId(userId));
	}

	@PutMapping("/update")
	public ResponseEntity<UserDTO> updateUserByUserId(@RequestBody UserDTO userDTO) {
		return getUser(userService.updateUserByUserId(userDTO));
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<List<UserDTO>> deleteUserbyUserId(@PathVariable Long userId) {
		return getUsers(userService.deleteUserbyUserId(userId));
	}

	@GetMapping("/search/firstname/{firstName}")
	public ResponseEntity<UserDTO> getUserbyFirstName(@PathVariable String firstName) {
		return getUser(userService.getUserbyFirstName(firstName));
	}

	@GetMapping("/getall/pagination")
	public ResponseEntity<List<UserDTO>> getUsersUsingPagination(@RequestParam int pageBatchNumber,
			@RequestParam int pageSize) {
		return getUsers(userService.getUsersUsingPagination(pageBatchNumber, pageSize));
	}

	@GetMapping("/serach/pagination/{firstName}")
	public ResponseEntity<List<UserDTO>> getUsersByFirstNameUsingPagination(@PathVariable String firstName,
			@RequestParam int pageBatchNumber, @RequestParam int pageSize) {
		return getUsers(userService.getUsersByFirstNameUsingPagination(firstName, pageBatchNumber, pageSize));
	}

	private ResponseEntity<List<UserDTO>> getUsers(List<UserDTO> userDTOList) {
		if (null != userDTOList) {
			return new ResponseEntity<List<UserDTO>>(userDTOList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<UserDTO>>(userDTOList, HttpStatus.NO_CONTENT);
		}
	}

	private ResponseEntity<UserDTO> getUser(UserDTO userDTO) {
		if (null != userDTO) {
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.NO_CONTENT);
		}
	}

}
