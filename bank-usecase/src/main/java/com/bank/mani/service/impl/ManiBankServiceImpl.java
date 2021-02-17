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

import com.bank.mani.dto.AccountDTO;
import com.bank.mani.dto.AccountStatement;
import com.bank.mani.dto.CustomerDTO;
import com.bank.mani.dto.TransactionHistoryDTO;
import com.bank.mani.entity.AccountEO;
import com.bank.mani.entity.AccountHistoryEO;
import com.bank.mani.entity.CustomerEO;
import com.bank.mani.entity.TransactionHistoryEO;
import com.bank.mani.exception.BankBusinessException;
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
	public TransactionHistoryDTO doFundTransfer(Long fromAccountNumber, Long toAccountNumber, Long transferAmount)
			throws BankBusinessException, Exception {
		LOGGER.info("ManiBankServiceImpl | doFundTransfer | START");
		AccountDTO fromAccountDTO = accountValidation(accountRepo.findById(fromAccountNumber));
		if (transferAmount <= fromAccountDTO.getAmount()) {

			AccountDTO toAccountDTO = accountValidation(accountRepo.findById(toAccountNumber));
			Long fromAccountBalance = fromAccountDTO.getAmount() - transferAmount;
			Long toAccountBalance = toAccountDTO.getAmount() + transferAmount;
			fromAccountDTO.setAmount(fromAccountBalance);
			toAccountDTO.setAmount(toAccountBalance);
			LOGGER.info("ManiBankServiceImpl | doFundTransfer | END");
			return fundTranactionSave(fromAccountDTO, toAccountDTO, transferAmount);
		} else {
			throw new BankBusinessException(
					"Not sufficient balance in ths account " + fromAccountDTO.getAccountNumber());
		}
	}

	@Override
	public List<AccountStatement> getAccountStatements(Long accountNumber, String month, String year)
			throws IllegalArgumentException, BankBusinessException {
		List<AccountStatement> accountStatementList = new ArrayList<>();
		LOGGER.info("ManiBankServiceImpl | getAccountStatements | START");
		accountValidation(accountRepo.findById(accountNumber));

		Month monthOfYear = Month.valueOf(month.toUpperCase());
		LocalDateTime startDate = YearMonth.of(2021, monthOfYear).atDay(1).atStartOfDay();
		LocalDateTime endDate = YearMonth.of(2021, monthOfYear).atEndOfMonth().atTime(23, 59, 59, 999);

		List<AccountHistoryEO> accountHistoryEOList = accountHistoryRepo.findByTranactionDateBetween(startDate,
				endDate);

		accountHistoryEOList = accountHistoryEOList.stream()
				.filter(accountHistoryEO -> accountHistoryEO.getAccountEO().getAccountNumber().equals(accountNumber))
				.collect(Collectors.toList());

		accountHistoryEOList
				.forEach(accountHistoryEO -> accountStatementList.add(mapToAccountStatement(accountHistoryEO)));

		LOGGER.info("ManiBankServiceImpl | getAccountStatements | END");
		return accountStatementList;
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

		transactionHistoryDTO.setFromAccountNumber(fromAccountDTO.getAccountNumber());
		transactionHistoryDTO.setToAccountNumber(toAccountDTO.getAccountNumber());
		transactionHistoryDTO.setTransferAmount(transferAmount);
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

	private AccountStatement mapToAccountStatement(AccountHistoryEO accountHistoryEO) {
		AccountStatement accountStatement = new AccountStatement();

		accountStatement.setAccountNumber(accountHistoryEO.getAccountEO().getAccountNumber());
		accountStatement.setBalanceAmount(accountHistoryEO.getBalanceAmount());

		String creditAmount = accountHistoryEO.getCreditAmount() == null ? " "
				: accountHistoryEO.getCreditAmount().toString();
		accountStatement.setCreditAmount(creditAmount);

		String debitAmount = accountHistoryEO.getDebitAmount() == null ? " "
				: accountHistoryEO.getDebitAmount().toString();
		accountStatement.setDebitAmount(debitAmount);

		accountStatement.setTranactionDate(accountHistoryEO.getTranactionDate());

		return accountStatement;
	}

	private CustomerDTO mapToDTO(CustomerEO customerEO) {
		return modelMapper.map(customerEO, CustomerDTO.class);
	}

	private CustomerEO mapToEO(CustomerDTO customerDTO) {
		return modelMapper.map(customerDTO, CustomerEO.class);
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
