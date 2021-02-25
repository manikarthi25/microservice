package com.bank.mani.example;

import java.time.LocalDateTime;

import com.bank.mani.entity.AccountEO;
import com.bank.mani.entity.AccountHistoryEO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransHistory {
	
	private Long accountHistoryId;
	
	private Long fromAccountNumber;
	
	private Long toAccountNumber;

	private Long transferAmount;

	private Long fromAccountClosingBalance;
	
	private Long toAccountClosingBalance;
	
	private LocalDateTime tranactionDate;


}
