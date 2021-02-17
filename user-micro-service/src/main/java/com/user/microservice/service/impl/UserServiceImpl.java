package com.user.microservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.microservice.dto.User;
import com.user.microservice.entity.UserEO;
import com.user.microservice.repo.UserRepo;
import com.user.microservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

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
