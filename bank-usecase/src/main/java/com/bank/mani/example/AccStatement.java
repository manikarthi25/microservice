package com.bank.mani.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bank.mani.model.AccountHistory;
import com.bank.mani.model.AccountStatement;

public class AccStatement {

	static List<TransHistory> transHistoryList = new ArrayList<>();

	static {
		transHistoryList.add(new TransHistory((long) 1, (long) 101, (long) 102, (long) 100, (long) 1000, (long) 1100,
				LocalDateTime.now()));
		transHistoryList.add(new TransHistory((long) 2, (long) 102, (long) 101, (long) 50, (long) 1050, (long) 1050,
				LocalDateTime.now()));
		transHistoryList.add(new TransHistory((long) 3, (long) 103, (long) 104, (long) 100, (long) 1000, (long) 1100,
				LocalDateTime.now()));
		transHistoryList.add(new TransHistory((long) 4, (long) 103, (long) 104, (long) 100, (long) 900, (long) 1200,
				LocalDateTime.now()));
		transHistoryList.add(new TransHistory((long) 5, (long) 105, (long) 106, (long) 100, (long) 500, (long) 600,
				LocalDateTime.now()));
		transHistoryList.add(new TransHistory((long) 6, (long) 106, (long) 105, (long) 50, (long) 550, (long) 550,
				LocalDateTime.now()));
		transHistoryList.add(new TransHistory((long) 7, (long) 106, (long) 105, (long) 50, (long) 600, (long) 500,
				LocalDateTime.now()));
	}

	public static void main(String[] args) {

		AccountStatement accountStatement = new AccountStatement();
		List<AccountHistory> accountHistoryList = new ArrayList<>();

		Long accountNumber = (long) 101;

		for (TransHistory transHistory : transHistoryList) {
			if (accountNumber == transHistory.getFromAccountNumber()
					|| accountNumber == transHistory.getToAccountNumber()) {
				AccountHistory accountHistory = new AccountHistory();
				accountHistory.setTranactionDate(transHistory.getTranactionDate());
				accountHistoryList.add(accountHistory);
				System.out.println(transHistory);
			} else {
				System.out.println("No data found");
			}
		} 
		accountStatement.setAccountNumber(accountNumber);
		accountStatement.setAccountHistoryList(accountHistoryList);
	}

}