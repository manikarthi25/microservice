package com.bank.mani.service;

import java.util.List;

import com.bank.mani.dto.AccountStatement;
import com.bank.mani.dto.TransactionHistoryDTO;
import com.bank.mani.exception.BankBusinessException;

public interface ManiBankService {

	TransactionHistoryDTO doFundTransfer(Long fromAccountNumber, Long toAccountNumber, Long transferAmount)
			throws BankBusinessException, Exception;

	List<AccountStatement> getAccountStatements(Long accountNumber, String month, String year)
			throws IllegalArgumentException, BankBusinessException;

}
