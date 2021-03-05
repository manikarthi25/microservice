package com.train.user.service.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.train.user.dto.UserDTO;
import com.train.user.entity.UserEO;
import com.train.user.repo.UserRepo;
import com.train.user.request.model.UserRequestModel;
import com.train.user.response.model.UserResponseModel;
import com.train.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepo userRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private Environment environment;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public UserServiceImpl(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder, Environment environment) {
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.environment = environment;
	}

	@Override
	public UserResponseModel addUser(UserRequestModel userRequestModel) {

		UserEO userEO = new UserEO();
		ModelMapper modelMapper = new ModelMapper();

		userEO.setUserSecurityId(UUID.randomUUID().toString());
		userEO.setEncryptedPassword(bCryptPasswordEncoder.encode(userRequestModel.getPassword()));
		userEO.setEmail(userRequestModel.getEmail());
		userEO.setFirstName(userRequestModel.getFirstName());
		userEO.setLastName(userRequestModel.getLastName());

		UserEO userEORes = userRepo.save(userEO);
		return getUserResponseModel(modelMapper.map(userEORes, UserDTO.class));
	}

	private UserResponseModel getUserResponseModel(UserDTO userDTO) {
		UserResponseModel userResponseModel = new UserResponseModel();
		userResponseModel.setUserId(userDTO.getUserId());
		userResponseModel.setEmail(userDTO.getEmail());
		userResponseModel.setFirstName(userDTO.getFirstName());
		userResponseModel.setLastName(userDTO.getLastName());
		return userResponseModel;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEO userEO = userRepo.findByEmail(email);

		if (userEO == null)
			throw new UsernameNotFoundException(email);

		return new User(userEO.getEmail(), userEO.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {
		UserEO userEO = userRepo.findByEmail(email);

		if (userEO == null)
			throw new UsernameNotFoundException(email);
		return new ModelMapper().map(userEO, UserDTO.class);
	}

	@Override
	public UserDTO getUserDetailsByUserId(Long userId) {
		Optional<UserEO> optionalUserEO = userRepo.findById(userId);

		if (!optionalUserEO.isPresent())
			throw new UsernameNotFoundException("User Not Found");
		return new ModelMapper().map(optionalUserEO.get(), UserDTO.class);
	}

}
