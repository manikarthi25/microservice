package com.bank.mani.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {

	private Long transactionId;

	private Long fromAccountNumber;

	private Long ToAccountNumber;

	private Long transferAmount;

	private String transferStatus;

	private LocalDateTime tranactionTS;

}
