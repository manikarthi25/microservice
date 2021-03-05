package com.bank.mani.service;

import com.bank.mani.exception.BankBusinessException;
import com.bank.mani.model.AccountStatement;
import com.bank.mani.model.MoneyTransfer;
import com.bank.mani.model.TransactionHistory;

public interface ManiBankService {

	TransactionHistory doFundTransfer(MoneyTransfer moneyTransfer) throws BankBusinessException, Exception;

	AccountStatement getAccountStatements(Long accountNumber, String month, int year)
			throws IllegalArgumentException, BankBusinessException;

}
