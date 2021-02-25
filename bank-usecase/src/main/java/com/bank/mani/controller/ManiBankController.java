package com.bank.mani.controller;

import java.time.Duration;
import java.time.Instant;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.mani.exception.BankBusinessException;
import com.bank.mani.model.AccountStatement;
import com.bank.mani.model.MoneyTransfer;
import com.bank.mani.model.TransactionHistory;
import com.bank.mani.service.ManiBankService;

@RestController
@RequestMapping("/bank")
@Validated
public class ManiBankController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ManiBankController.class);

	@Autowired
	private ManiBankService maniBankService;

	@PostMapping("/money-transfer")
	public ResponseEntity<TransactionHistory> doFundTransfer(@Valid @RequestBody MoneyTransfer moneyTransfer)
			throws BankBusinessException, Exception {

		Instant startTime = Instant.now();
		LOGGER.info("Fund Transfer start Time : " + startTime);

		TransactionHistory transactionHistory = maniBankService.doFundTransfer(moneyTransfer);

		Instant endTime = Instant.now();
		LOGGER.info("Fund Transfer end time : " + endTime);
		Duration timeElapsed = Duration.between(startTime, endTime);
		LOGGER.info("Total time for Fund Transfering : " + timeElapsed);

		return new ResponseEntity<TransactionHistory>(transactionHistory, HttpStatus.OK);
	}

	@GetMapping("/account/statements")
	public ResponseEntity<AccountStatement> getAccountStatements(@RequestParam @NotNull @Positive Long accountNumber,
			@RequestParam @NotEmpty String month, @RequestParam @Positive @NotNull @Size(max = 4) int year)
			throws IllegalArgumentException, BankBusinessException, MethodArgumentNotValidException {

		Instant startTime = Instant.now();
		LOGGER.info("Account Statement start Time : " + startTime);

		AccountStatement accountStatement = maniBankService.getAccountStatements(accountNumber, month, year);

		Instant endTime = Instant.now();
		LOGGER.info("Account Statement end time : " + endTime);
		Duration timeElapsed = Duration.between(startTime, endTime);
		LOGGER.info("Total time for Account Statement : " + timeElapsed);

		if (!ObjectUtils.isEmpty(accountStatement)) {
			return new ResponseEntity<AccountStatement>(accountStatement, HttpStatus.OK);
		} else {
			return new ResponseEntity<AccountStatement>(accountStatement, HttpStatus.NO_CONTENT);
		}
	}

}
