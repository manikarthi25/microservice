package com.train.user.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.train.user.entity.UserEO;

@Repository
public interface UserRepo extends CrudRepository<UserEO, Long> {

	UserEO findByEmail(String username);

	UserEO findByUserId(String userId);

}
