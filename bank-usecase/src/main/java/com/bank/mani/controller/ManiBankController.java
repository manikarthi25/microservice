package com.bank.mani.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.mani.dto.AccountStatement;
import com.bank.mani.dto.TransactionHistoryDTO;
import com.bank.mani.exception.BankBusinessException;
import com.bank.mani.service.ManiBankService;

@RestController
@RequestMapping("/bank")
public class ManiBankController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ManiBankController.class);

	@Autowired
	private ManiBankService maniBankService;

	@GetMapping("/status")
	public String getAppStatus() {
		LOGGER.info("Test log file - Application up and running");
		return "Application up and running";
	}

	@PostMapping("/fundtransfer")
	public ResponseEntity<TransactionHistoryDTO> doFundTransfer(@RequestParam @NotNull @Positive Long fromAccountNumber,
			@RequestParam @NotNull @Positive Long toAccountNumber, @RequestParam @NotNull @Positive Long transferAmount)
			throws Exception, MethodArgumentNotValidException {

		Instant startTime = Instant.now();
		LOGGER.info("Fund Transfer start Time : " + startTime);

		TransactionHistoryDTO transactionHistoryDTO = maniBankService.doFundTransfer(fromAccountNumber, toAccountNumber,
				transferAmount);

		Instant endTime = Instant.now();
		LOGGER.info("Fund Transfer end time : " + endTime);
		Duration timeElapsed = Duration.between(startTime, endTime);
		LOGGER.info("Total time for Fund Transfering : " + timeElapsed);

		return new ResponseEntity<TransactionHistoryDTO>(transactionHistoryDTO, HttpStatus.OK);
	}

	@GetMapping("/statement")
	public ResponseEntity<List<AccountStatement>> getAccountStatements(
			@RequestParam @NotNull @Positive Long accountNumber, @RequestParam @NotEmpty String month,
			@RequestParam @NotNull @Size(max = 4) String year)
			throws IllegalArgumentException, BankBusinessException, MethodArgumentNotValidException {

		Instant startTime = Instant.now();
		LOGGER.info("Account Statement start Time : " + startTime);

		List<AccountStatement> accountStatementList = maniBankService.getAccountStatements(accountNumber, month, year);

		Instant endTime = Instant.now();
		LOGGER.info("Account Statement end time : " + endTime);
		Duration timeElapsed = Duration.between(startTime, endTime);
		LOGGER.info("Total time for Account Statement : " + timeElapsed);

		if (!CollectionUtils.isEmpty(accountStatementList)) {
			return new ResponseEntity<List<AccountStatement>>(accountStatementList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<AccountStatement>>(accountStatementList, HttpStatus.NO_CONTENT);
		}
	}

}
