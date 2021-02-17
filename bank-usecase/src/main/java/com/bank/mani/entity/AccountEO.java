package com.bank.mani.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "account")
public class AccountEO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_number")
	private Long accountNumber;

	@Column(columnDefinition = "char(1) default 'N'")
	private String activeAccount;

	private String accountType;

	private Long amount;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "customerId", nullable = false)
	private CustomerEO customerEO;

}
