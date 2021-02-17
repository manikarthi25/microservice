package com.bank.mani.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.mani.entity.AccountEO;

@Repository
public interface AccountRepo extends JpaRepository<AccountEO, Long> {

}
