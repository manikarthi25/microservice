package com.train.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.train.user.entity.UserEO;

@Repository
public interface UserRepo extends JpaRepository<UserEO, Long> {

	UserEO findByEmail(String username);

}
