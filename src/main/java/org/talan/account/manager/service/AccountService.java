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
import org.talan.account.manager.utils.AccountManagerConstants;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

	public void withdraw(OperationDto operationDto) throws OperationException {
		Account account = accountsMap.get(operationDto.getAccountId());
		Float amount = operationDto.getAmount();
		
		if(account == null) {
			log.error(AccountManagerConstants.ACCOUNT_CAN_T_BE_NULL);
			throw new OperationException(AccountManagerConstants.ACCOUNT_CAN_T_BE_NULL);
		}
		
		if(amount > account.getBalance() || amount < 0){
			log.error(AccountManagerConstants.BALANCE_CANT_BE_LOWER_THAN_AMOUNT);
			throw new OperationException(AccountManagerConstants.BALANCE_CANT_BE_LOWER_THAN_AMOUNT);
		}
	
		account.setBalance(account.getBalance() - amount);
	}

	public void deposit(OperationDto operationDto) throws OperationException {
		Account account = accountsMap.get(operationDto.getAccountId());
		Float amount = operationDto.getAmount();
		
		if(account == null) {
			log.error(AccountManagerConstants.ACCOUNT_CAN_T_BE_NULL);
			throw new OperationException(AccountManagerConstants.ACCOUNT_CAN_T_BE_NULL);
		}
		
		if(amount < 0){
			log.error(AccountManagerConstants.AMOUNT_CAN_T_BE_NEGATIVE);
			throw new OperationException(AccountManagerConstants.AMOUNT_CAN_T_BE_NEGATIVE);
		}
		
		account.setBalance(account.getBalance() + amount);
	}

	public void transferAmount(TransactionDto transactionDto) throws OperationException, TransferException{
		Account account = accountsMap.get(transactionDto.getPayerId());
		Float amount = transactionDto.getAmount();
		Account desitnationAccount = accountsMap.get(transactionDto.getPayeeId());
		
		if(account == null) {
			log.error(AccountManagerConstants.PAYER_CAN_T_BE_NULL);
			throw new TransferException(AccountManagerConstants.PAYER_CAN_T_BE_NULL);
		}
		
		if(desitnationAccount == null){
			log.error(AccountManagerConstants.PAYEE_CAN_T_BE_NULL);
			throw new TransferException(AccountManagerConstants.PAYEE_CAN_T_BE_NULL);
		}
		
		if(amount < 0 || amount > account.getBalance()){
			log.error(AccountManagerConstants.BALANCE_CANT_BE_LOWER_THAN_AMOUNT);
			throw new TransferException(AccountManagerConstants.BALANCE_CANT_BE_LOWER_THAN_AMOUNT);
		}
		
		withdraw(new OperationDto(account.getAccountId(), amount));
		addTransactionHistory(account, amount, Directions.OUT.getCode(), desitnationAccount.getAccountId());
		
		deposit(new OperationDto(desitnationAccount.getAccountId(), amount));
		addTransactionHistory(desitnationAccount, amount, Directions.IN.getCode(), account.getAccountId());
		
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
