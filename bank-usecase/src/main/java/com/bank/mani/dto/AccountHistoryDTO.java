package com.bank.mani.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(Include.NON_NULL)
public class AccountHistoryDTO {

	private Long accountHistoryId;

	private LocalDateTime tranactionDate;

	private Long debitAmount;

	private Long creditAmount;

	private Long balanceAmount;
	
	private Long accountNumber;

	private AccountDTO accountDTO;

}
