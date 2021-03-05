package com.bank.mani.service.impl;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bank.mani.dto.AccountDTO;
import com.bank.mani.dto.CustomerDTO;
import com.bank.mani.dto.TransactionHistoryDTO;
import com.bank.mani.entity.AccountEO;
import com.bank.mani.entity.AccountHistoryEO;
import com.bank.mani.entity.CustomerEO;
import com.bank.mani.entity.TransactionHistoryEO;
import com.bank.mani.exception.BankBusinessException;
import com.bank.mani.model.AccountHistory;
import com.bank.mani.model.AccountStatement;
import com.bank.mani.model.MoneyTransfer;
import com.bank.mani.model.TransactionHistory;
import com.bank.mani.repo.AccountHistoryRepo;
import com.bank.mani.repo.AccountRepo;
import com.bank.mani.repo.TranactionHistoryRepo;
import com.bank.mani.service.ManiBankService;
import com.bank.mani.util.ManiBankConsonents;
import com.bank.mani.util.ManiBankUtils;

@Service
public class ManiBankServiceImpl implements ManiBankService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ManiBankServiceImpl.class);

	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private AccountHistoryRepo accountHistoryRepo;

	@Autowired
	private TranactionHistoryRepo tranactionHistoryRepo;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public TransactionHistory doFundTransfer(MoneyTransfer moneyTransfer) throws BankBusinessException, Exception {
		LOGGER.info("ManiBankServiceImpl | doFundTransfer | START");

		Long fromAccountNumber = moneyTransfer.getFromAccountNumber();
		Long toAccountNumber = moneyTransfer.getToAccountNumber();
		Long transferAmount = moneyTransfer.getTransferAmount();
		
		if (ManiBankConsonents.ZERO >= transferAmount) {
			throw new BankBusinessException("Check Transfer amount - You can transfer Minimum Rs 1 : Your Transfer amount is :" + transferAmount);
		}
		
		AccountDTO fromAccountDTO = accountValidation(accountRepo.findById(fromAccountNumber));
		if (transferAmount <= fromAccountDTO.getAmount()) {

			AccountDTO toAccountDTO = accountValidation(accountRepo.findById(toAccountNumber));
			Long fromAccountBalance = fromAccountDTO.getAmount() - transferAmount;
			Long toAccountBalance = toAccountDTO.getAmount() + transferAmount;
			fromAccountDTO.setAmount(fromAccountBalance);
			toAccountDTO.setAmount(toAccountBalance);
			LOGGER.info("ManiBankServiceImpl | doFundTransfer | END");
			TransactionHistoryDTO transactionHistoryDTO = fundTranactionSave(fromAccountDTO, toAccountDTO,
					transferAmount);
			return getTransactionHistory(transactionHistoryDTO, fromAccountNumber, toAccountNumber, transferAmount);

		} else {
			throw new BankBusinessException(
					"Not sufficient balance in ths account " + fromAccountDTO.getAccountNumber());
		}
	}

	private TransactionHistory getTransactionHistory(TransactionHistoryDTO transactionHistoryDTO,
			Long fromAccountNumber, Long toAccountNumber, Long transferAmount) {
		TransactionHistory transactionHistory = new TransactionHistory();
		if (ObjectUtils.isNotEmpty(transactionHistoryDTO)) {
			transactionHistory.setTransactionId(transactionHistoryDTO.getTransactionId());
			transactionHistory.setFromAccountNumber(fromAccountNumber);
			transactionHistory.setToAccountNumber(toAccountNumber);
			transactionHistory.setTransferStatus(transactionHistoryDTO.getTransferStatus());
			transactionHistory.setTransferAmount(transferAmount);
			transactionHistory.setTranactionTS(transactionHistoryDTO.getTranactionTS());
			return transactionHistory;
		} else {
			return null;
		}
	}

	@Override
	public AccountStatement getAccountStatements(Long accountNumber, String month, int year)
			throws IllegalArgumentException, BankBusinessException {

		AccountStatement accountStatement = new AccountStatement();
		List<AccountHistory> accountHistoryList = new ArrayList<>();

		LOGGER.info("ManiBankServiceImpl | getAccountStatements | START");
		accountValidation(accountRepo.findById(accountNumber));

		Month monthOfYear = Month.valueOf(month.toUpperCase());
		LocalDateTime startDate = YearMonth.of(year, monthOfYear).atDay(1).atStartOfDay();
		LocalDateTime endDate = YearMonth.of(year, monthOfYear).atEndOfMonth().atTime(23, 59, 59, 999);

		List<AccountHistoryEO> accountHistoryEOList = accountHistoryRepo.findByTranactionDateBetween(startDate,
				endDate);

		accountStatement.setAccountNumber(accountNumber);

		accountHistoryEOList = accountHistoryEOList.stream()
				.filter(accountHistoryEO -> accountHistoryEO.getAccountEO().getAccountNumber().equals(accountNumber))
				.collect(Collectors.toList());

		accountHistoryEOList
				.forEach(accountHistoryEO -> accountHistoryList.add(mapToAccountStatement(accountHistoryEO)));
		if (!CollectionUtils.isEmpty(accountHistoryList)) {
			accountStatement.setAccountNumber(accountNumber);
			accountStatement.setAccountHistoryList(accountHistoryList);
			return accountStatement;
		} else {
			return null;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	private TransactionHistoryDTO fundTranactionSave(AccountDTO fromAccountDTO, AccountDTO toAccountDTO,
			Long transferAmount) throws Exception {

		TransactionHistoryDTO transactionHistoryDTO = new TransactionHistoryDTO();
		AccountHistoryEO fromAcountHistory = new AccountHistoryEO();
		AccountHistoryEO toAcountHistory = new AccountHistoryEO();
		AccountHistoryEO fromAcountHistoryEO = getAccountHistoryEO(fromAccountDTO, transferAmount,
				ManiBankConsonents.FROMACCOUNT);
		AccountHistoryEO toAcountHistoryEO = getAccountHistoryEO(toAccountDTO, transferAmount,
				ManiBankConsonents.TOACCOUNT);

		fromAcountHistory = accountHistoryRepo.save(fromAcountHistoryEO);
		accountRepo.saveAndFlush(fromAcountHistoryEO.getAccountEO());

		toAcountHistory = accountHistoryRepo.save(toAcountHistoryEO);
		accountRepo.saveAndFlush(toAcountHistoryEO.getAccountEO());

		transactionHistoryDTO.setFromAccountHostoryId(fromAcountHistory.getAccountHistoryId());
		transactionHistoryDTO.setToAccountHistoryId(toAcountHistory.getAccountHistoryId());
		transactionHistoryDTO.setTranactionTS(LocalDateTime.now());

		if (ObjectUtils.isNotEmpty(fromAcountHistory) && ObjectUtils.isNotEmpty(toAcountHistory)) {
			transactionHistoryDTO.setTransferStatus("Tranaction Success");
		} else {
			transactionHistoryDTO.setTransferStatus("Tranaction Failed");
		}

		TransactionHistoryEO transactionHistoryEO = tranactionHistoryRepo.save(mapToEO(transactionHistoryDTO));
		return mapToDTO(transactionHistoryEO);

	}

	private AccountHistoryEO getAccountHistoryEO(AccountDTO accountDTO, Long transferAmount, String account) {
		AccountHistoryEO accountHistoryEO = new AccountHistoryEO();
		accountHistoryEO.setTranactionDate(ManiBankUtils.getFormattedLocalDateTime(LocalDateTime.now()));
		accountHistoryEO.setBalanceAmount(accountDTO.getAmount());

		if (ManiBankConsonents.FROMACCOUNT.equalsIgnoreCase(account))
			accountHistoryEO.setDebitAmount(transferAmount);
		else
			accountHistoryEO.setCreditAmount(transferAmount);

		accountHistoryEO.setAccountEO(mapToEO(accountDTO));
		accountHistoryEO
				.setTranactionDate(ManiBankUtils.getFormattedLocalDateTime(accountHistoryEO.getTranactionDate()));
		return accountHistoryEO;
	}

	private AccountDTO accountValidation(Optional<AccountEO> acountEO) throws BankBusinessException {
		if (acountEO.isPresent()) {
			AccountEO accountEntity = acountEO.get();
			CustomerEO customerEO = accountEntity.getCustomerEO();
			AccountDTO accountDTO = mapToDTO(accountEntity);
			accountDTO.setCustomerDTO(mapToDTO(customerEO));
			String acccountStatus = accountDTO.getActiveAccount();
			if (ManiBankConsonents.ISACTIVE.equalsIgnoreCase(acccountStatus))
				return accountDTO;
			else
				throw new BankBusinessException(accountDTO.getAccountNumber() + " - This account is inactive");
		} else {
			throw new BankBusinessException("Invalid account number");
		}
	}

	private AccountHistory mapToAccountStatement(AccountHistoryEO accountHistoryEO) {

		AccountHistory accountHistory = new AccountHistory();
		accountHistory.setBalanceAmount(accountHistoryEO.getBalanceAmount());
		String creditAmount = accountHistoryEO.getCreditAmount() == null ? " "
				: accountHistoryEO.getCreditAmount().toString();
		accountHistory.setCreditAmount(creditAmount);
		String debitAmount = accountHistoryEO.getDebitAmount() == null ? " "
				: accountHistoryEO.getDebitAmount().toString();
		accountHistory.setDebitAmount(debitAmount);
		accountHistory.setTranactionDate(accountHistoryEO.getTranactionDate());
		return accountHistory;
	}

	private CustomerDTO mapToDTO(CustomerEO customerEO) {
		return modelMapper.map(customerEO, CustomerDTO.class);
	}

	private AccountDTO mapToDTO(AccountEO accountEO) {
		return modelMapper.map(accountEO, AccountDTO.class);
	}

	private AccountEO mapToEO(AccountDTO accountDTO) {
		return modelMapper.map(accountDTO, AccountEO.class);
	}

	private TransactionHistoryDTO mapToDTO(TransactionHistoryEO transactionHistoryEO) {
		return modelMapper.map(transactionHistoryEO, TransactionHistoryDTO.class);
	}

	private TransactionHistoryEO mapToEO(TransactionHistoryDTO transactionHistoryDTO) {
		return modelMapper.map(transactionHistoryDTO, TransactionHistoryEO.class);
	}

}
