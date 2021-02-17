package com.bank.mani.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountStatement {

	@NotNull
	private LocalDateTime tranactionDate;

	private String debitAmount;

	private String creditAmount;

	@NotNull
	@Positive
	private Long balanceAmount;

	@NotNull
	@Positive
	private Long accountNumber;

}
