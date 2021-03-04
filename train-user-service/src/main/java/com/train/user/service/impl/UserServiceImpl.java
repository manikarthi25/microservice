package com.train.user.service.impl;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
	public UserDTO addUser(UserDTO userDTO) {

		userDTO.setUserId(UUID.randomUUID().toString());
		userDTO.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		userRepo.save(modelMapper.map(userDTO, UserEO.class));
		UserDTO user = modelMapper.map(userDTO, UserDTO.class);
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEO userEO = userRepo.findByEmail(username);

		if (userEO == null)
			throw new UsernameNotFoundException(username);

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
	public UserDTO getUserDetailsByUserId(String userId) {
		UserEO userEO = userRepo.findByUserId(userId);

		if (userEO == null)
			throw new UsernameNotFoundException("User Not Found");
		return new ModelMapper().map(userEO, UserDTO.class);
	}

}
