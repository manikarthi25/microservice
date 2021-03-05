package com.bank.mani.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountHistory {

	private LocalDateTime tranactionDate;

	private String debitAmount;

	private String creditAmount;

	private Long balanceAmount;

}
