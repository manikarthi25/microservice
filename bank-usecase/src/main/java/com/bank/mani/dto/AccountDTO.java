package com.bank.mani.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountDTO {

	private Long accountNumber;

	private String activeAccount;

	private String accountType;

	private Long amount;

	private CustomerDTO customerDTO;

}
