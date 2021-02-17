package com.bank.mani.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "transaction_history")
public class TransactionHistoryEO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long transactionId;

	private Long fromAccountNumber;

	private Long ToAccountNumber;

	private Long transferAmount;

	private String transferStatus;

	@Column(name = "transaction_ts")
	private LocalDateTime tranactionTS;

}
