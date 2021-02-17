package com.bank.mani.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.mani.entity.AccountHistoryEO;

@Repository
public interface AccountHistoryRepo extends JpaRepository<AccountHistoryEO, Long> {

	List<AccountHistoryEO> findByTranactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
