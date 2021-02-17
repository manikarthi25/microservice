package com.bank.mani.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.mani.entity.CustomerEO;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEO, Long> {

}
