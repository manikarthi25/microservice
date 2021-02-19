package com.demo.springbatch.batch;

import java.util.Optional;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.springbatch.entity.UserEO;
import com.demo.springbatch.repo.UserRepo;

@Component
public class Processor implements ItemProcessor<UserEO, UserEO> {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserEO process(UserEO userEO) throws Exception {
		Optional<UserEO> userEOFromDb = userRepo.findById(userEO.getUserid());
		if (userEOFromDb.isPresent()) {
			userEO.setEmail(userEO.getEmail());
		}
		return userEO;
	}

}
