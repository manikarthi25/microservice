package com.bank.mani.model;

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
public class MoneyTransfer {

	@NotNull
	@Positive
	private Long fromAccountNumber;

	@NotNull
	@Positive
	private Long toAccountNumber;

	private Long transferAmount;

}
