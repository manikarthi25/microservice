package com.bank.mani.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountStatement {

	private Long accountNumber;

	private List<AccountHistory> accountHistoryList;

}
