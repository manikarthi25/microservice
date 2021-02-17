package com.bank.mani.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "account_history")
public class AccountHistoryEO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long accountHistoryId;

	private LocalDateTime tranactionDate;

	private Long debitAmount;

	private Long creditAmount;

	private Long balanceAmount;

	@ManyToOne
	@JoinColumn(name = "accountNumber")
	private AccountEO accountEO;

}
