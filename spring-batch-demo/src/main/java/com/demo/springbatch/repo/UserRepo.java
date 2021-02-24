package com.demo.springbatch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.springbatch.entity.UserEO;

@Repository
public interface UserRepo extends JpaRepository<UserEO, Integer> {

}
