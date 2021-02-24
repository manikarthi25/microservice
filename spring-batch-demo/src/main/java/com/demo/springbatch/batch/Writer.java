package com.demo.springbatch.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.springbatch.entity.UserEO;
import com.demo.springbatch.repo.UserRepo;

@Component
public class Writer implements ItemWriter<UserEO> {

	@Autowired
	UserRepo userRepo;

	@Override
	@Transactional
	public void write(List<? extends UserEO> userEO) throws Exception {
		userRepo.saveAll(userEO);
	}

}
