package org.talan.account.manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.talan.account.manager.Exceptions.OperationException;
import org.talan.account.manager.Exceptions.TransferException;
import org.talan.account.manager.dto.OperationDto;
import org.talan.account.manager.dto.TransactionDto;
import org.talan.account.manager.dto.TransactionFilterDto;
import org.talan.account.manager.models.Account;
import org.talan.account.manager.models.Transaction;
import org.talan.account.manager.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@Api
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PutMapping("/account")
	@ApiOperation(httpMethod = "PUT", value = "Resource to create an account")
	public @ResponseBody void createAccount(@RequestBody Account account) {
		accountService.createAccount(account);
	}
	
	@GetMapping("/account/{accountId}")
	@ApiOperation(httpMethod = "GET", value = "Resource to get an account")
	public @ResponseBody ResponseEntity<Account> getAccount(@PathVariable String accountId) {
		Account account = accountService.findAccount(accountId);
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	@PostMapping("/withdraw")
	@ApiOperation(httpMethod = "POST", value = "Resource to perform the withdraw operation")
	@ApiResponse(code = 500, message = "An error occured while performing the action")
	public @ResponseBody void withdraw(@RequestBody OperationDto operationDto) throws OperationException {
			accountService.withdraw(operationDto);
	}

	@PostMapping("/deposit")
	@ApiOperation(httpMethod = "POST", value = "Resource to perform the deposit operation")
	@ApiResponse(code = 500, message = "An error occured while performing the action")
	public @ResponseBody void deposit(@RequestBody OperationDto operationDto) throws OperationException {
			accountService.deposit(operationDto);
	}

	@PostMapping("/transferamount")
	@ApiOperation(httpMethod = "POST", value = "Resource to perform the transfer operation")
	@ApiResponse(code = 500, message = "An error occured while performing the action")
	public @ResponseBody void transferAmount(@RequestBody TransactionDto transactionDto) throws OperationException, TransferException {
			accountService.transferAmount(transactionDto);
	}
	
	//TODO Change to get
	@PostMapping("/filtertransaction")
	@ApiOperation(httpMethod = "POST", value = "Resource to filter transactions history of a bank account")
	@ApiResponse(code = 500, message = "An error occured while performing the action")
	public @ResponseBody ResponseEntity<List<Transaction>> filterTransactionHistory(@RequestBody TransactionFilterDto transactionFilterDto){
		List<Transaction> transactions = accountService.filterTransactionHistory(transactionFilterDto);
		return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
	}

}
