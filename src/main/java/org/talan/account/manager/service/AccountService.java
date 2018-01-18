package org.talan.account.manager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.talan.account.manager.Exceptions.OperationException;
import org.talan.account.manager.Exceptions.TransferException;
import org.talan.account.manager.dto.OperationDto;
import org.talan.account.manager.dto.TransactionDto;
import org.talan.account.manager.dto.TransactionFilterDto;
import org.talan.account.manager.models.Account;
import org.talan.account.manager.models.Directions;
import org.talan.account.manager.models.Transaction;

import lombok.Getter;

@Service
public class AccountService {

	@Getter
	private Map<String, Account> accountsMap = new HashMap<>();
	
	public void resetAccounts() {
		this.accountsMap = new HashMap<>();
	}
	
	public void createAccount(Account account) {
		accountsMap.put(account.getAccountId(), account);
		
	}
	
	public Account findAccount(String accountId) {
		return this.accountsMap.get(accountId);
	}

	public boolean withdraw(OperationDto operationDto) throws Exception {
		Account account = accountsMap.get(operationDto.getAccountId());
		Float amount = operationDto.getAmount();
		
		if(account == null) {
			throw new OperationException("Account can't be null");
		}
		
		if(amount > account.getBalance() || amount < 0){
			throw new OperationException("balance cant be lower than amount");
		}
		
		account.setBalance(account.getBalance() - amount);
		return true;
	}

	public boolean deposit(OperationDto operationDto) throws OperationException {
		Account account = accountsMap.get(operationDto.getAccountId());
		Float amount = operationDto.getAmount();
		
		if(account == null) {
			throw new OperationException("Account can't be null");
		}
		
		if(amount < 0){
			throw new OperationException("amount can't be negative");
		}
		
		account.setBalance(account.getBalance() + amount);
		return true;
	}

	public boolean transferAmount(TransactionDto transactionDto) throws Exception{
		Account account = accountsMap.get(transactionDto.getPayerId());
		Float amount = transactionDto.getAmount();
		Account desitnationAccount = accountsMap.get(transactionDto.getPayeeId());
		
		if(account == null) {
			throw new TransferException("payer can't be null");
		}
		
		if(desitnationAccount == null){
			throw new TransferException("payee can't be null");
		}
		
		if(amount < 0 || amount > account.getBalance()){
			throw new TransferException("balance cant be lower than amount");
		}
		
		this.withdraw(new OperationDto(account.getAccountId(), amount));
		this.addTransactionHistory(account, amount, Directions.OUT.getCode(), desitnationAccount.getAccountId());
		
		this.deposit(new OperationDto(desitnationAccount.getAccountId(), amount));
		this.addTransactionHistory(desitnationAccount, amount, Directions.IN.getCode(), account.getAccountId());
		
		return true;
		
	}
	
	public List<Transaction> filterTransactionHistory(TransactionFilterDto transactionFilterDto){
		Account account = accountsMap.get(transactionFilterDto.getAccountId());
		Directions direction = transactionFilterDto.getDirection();
		
		return account.getTransactionHistory().stream()
				.filter(t -> t.getDirection().equals(direction.getCode()))
				.collect(Collectors.toList());
	}
	
	private void addTransactionHistory(Account account, Float amount, String direction, String thirdPartyId) {
		Transaction transaction = new Transaction(amount, direction, thirdPartyId); 
		account.getTransactionHistory().add(transaction);
	}
	
	
}
