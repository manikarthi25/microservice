package com.bank.mani.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
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

	private Long fromAccountHostoryId;

	private Long ToAccountHistoryId;

	private String transferStatus;

	@Column(name = "transaction_ts")
	private LocalDateTime tranactionTS;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "fromAccountHostoryId", referencedColumnName = "accountHistoryId", insertable = false, updatable = false),
			@JoinColumn(name = "ToAccountHistoryId", referencedColumnName = "accountHistoryId", insertable = false, updatable = false) })
	private AccountHistoryEO accountHistoryEO;

}
