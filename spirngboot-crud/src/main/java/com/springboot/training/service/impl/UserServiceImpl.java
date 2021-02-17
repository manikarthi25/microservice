package com.springboot.training.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.springboot.training.dto.UserDTO;
import com.springboot.training.dto.UserNamesDTO;
import com.springboot.training.eo.UserEO;
import com.springboot.training.repo.UserRepo;
import com.springboot.training.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDTO addNewUser(UserDTO userDTO) {
		UserEO userEO = userRepo.save(mapToEO(userDTO));
		return mapToDTO(userEO);
	}

	@Override
	public List<UserDTO> getAllUser() {
		List<UserDTO> userDTOList = new ArrayList<>();
		List<UserEO> userEOList = userRepo.findAll();
		userEOList.forEach(userEO -> userDTOList.add(mapToDTO(userEO)));
		return userDTOList;
	}

	@Override
	public List<UserNamesDTO> getUserFirstAndLastName() {
		return userRepo.getUserFirstNameAndLastName();
	}

	@Override
	public UserDTO getUserbyUserId(Long userId) {
		Optional<UserEO> userEOOptional = userRepo.findById(userId);
		if (userEOOptional.isPresent()) {
			return mapToDTO(userEOOptional.get());
		} else {
			return null;
		}
	}

	@Override
	public UserDTO updateUserByUserId(UserDTO userDTO) {
		UserDTO user = getUserbyUserId(userDTO.getUserId());
		if (null != user) {
			UserEO userEO = mapToEO(userDTO);
			return mapToDTO(userRepo.saveAndFlush(userEO));
		} else {
			return null;
		}
	}

	@Override
	public List<UserDTO> deleteUserbyUserId(Long userId) {
		UserDTO userDTO = getUserbyUserId(userId);
		if (null != userDTO) {
			userRepo.deleteById(userId);
			return getAllUser();
		} else {
			return null;
		}
	}

	@Override
	public UserDTO getUserbyFirstName(String firstName) {
		return mapToDTO(userRepo.findByFirstName(firstName));
	}

	@Override
	public List<UserDTO> getUsersUsingPagination(int pageBatchNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageBatchNumber, pageSize);
		List<UserDTO> userDTOList = new ArrayList<>();
		List<UserEO> userEOList = userRepo.findAll(pageable).getContent();
		userEOList.forEach(userEO -> userDTOList.add(mapToDTO(userEO)));
		return userDTOList;
	}

	public List<UserDTO> getUsersByFirstNameUsingPagination(String firstName, int pageBatchNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageBatchNumber, pageSize, Direction.ASC, "firstName");
		List<UserDTO> userDTOList = new ArrayList<>();
		List<UserEO> userEOList = userRepo.findByFirstName(firstName, pageable);
		userEOList.forEach(userEO -> userDTOList.add(mapToDTO(userEO)));
		return userDTOList;
	}

	private UserDTO mapToDTO(UserEO userEO) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userEO, UserDTO.class);
	}

	private UserEO mapToEO(UserDTO userDTO) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userDTO, UserEO.class);
	}

}
