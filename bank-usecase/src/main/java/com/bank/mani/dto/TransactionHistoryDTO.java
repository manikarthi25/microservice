package com.bank.mani.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryDTO {

	@NotNull
	@Positive
	private Long transactionId;

	@NotNull
	@Positive
	private Long fromAccountNumber;

	@NotNull
	@Positive
	private Long ToAccountNumber;

	@NotNull
	@Positive
	private Long transferAmount;

	@NotNull
	@NotEmpty
	private String transferStatus;

	@NotNull
	@FutureOrPresent
	private LocalDateTime tranactionTS;

}
