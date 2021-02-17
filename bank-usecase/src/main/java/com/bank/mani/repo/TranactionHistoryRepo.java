package com.bank.mani.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.mani.entity.TransactionHistoryEO;

public interface TranactionHistoryRepo extends JpaRepository<TransactionHistoryEO, Long> {

}
