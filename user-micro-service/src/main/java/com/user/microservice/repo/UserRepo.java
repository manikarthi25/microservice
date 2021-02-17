package com.user.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.microservice.entity.UserEO;

@Repository
public interface UserRepo extends JpaRepository<UserEO, Long> {

}
