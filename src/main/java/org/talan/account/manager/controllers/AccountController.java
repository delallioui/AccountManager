package org.talan.account.manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.talan.account.manager.Exceptions.OperationException;
import org.talan.account.manager.dto.OperationDto;
import org.talan.account.manager.dto.TransactionDto;
import org.talan.account.manager.dto.TransactionFilterDto;
import org.talan.account.manager.models.Account;
import org.talan.account.manager.models.Transaction;
import org.talan.account.manager.service.AccountService;
import org.talan.account.manager.utils.ResponseStat;

@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PutMapping("/account")
	public @ResponseBody ResponseStat createAccount(@RequestBody Account account) {
		accountService.createAccount(account);
		return ResponseStat.SUCCESS;
	}
	
	@GetMapping("/account/{accountId}")
	public @ResponseBody Account getAccount(@RequestAttribute String accountId) {
		return accountService.findAccount(accountId);
	}

	@PostMapping("/withdraw")
	public @ResponseBody ResponseStat withdraw(@RequestBody OperationDto operationDto) {
		try {
			accountService.withdraw(operationDto);
			return ResponseStat.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseStat.FAILURE(e.getMessage());
		}
	}

	@PostMapping("/deposit")
	public @ResponseBody ResponseStat deposit(@RequestBody OperationDto operationDto) {
		try {
			accountService.deposit(operationDto);
			return ResponseStat.SUCCESS;
		} catch (OperationException e) {
			e.printStackTrace();
			return ResponseStat.FAILURE(e.getMessage());
		}
	}

	@PostMapping("/transferamount")
	public @ResponseBody ResponseStat transferAmount(@RequestBody TransactionDto transactionDto) {
		try {
			accountService.transferAmount(transactionDto);
			return ResponseStat.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseStat.FAILURE(e.getMessage());
		}
	}
	
	//TODO Change to get
	@PostMapping("/filtertransaction")
	public @ResponseBody List<Transaction> filterTransactionHistory(@RequestBody TransactionFilterDto transactionFilterDto){
		return accountService.filterTransactionHistory(transactionFilterDto);
	}

}
