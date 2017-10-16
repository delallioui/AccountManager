package account.manager.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Account {
	private String accountId;
	private int balance;
	private List<Transaction> transactionHistory = new ArrayList<>();
	
	public void withdraw(int amount){
		if(amount > this.balance || amount < 0){
			return;
		}
		
		this.balance -= amount;
	}
	
	public void deposit(int amount){
		if(amount < 0){
			return;
		}
		
		this.balance += amount;
	}
	
	public Account transferAmount(int amount, Account desitnationAccount){
		if(desitnationAccount == null){
			return null;
		}
		
		if(amount < 0 || amount > this.getBalance()){
			return desitnationAccount;
		}
		
		this.withdraw(amount);
		this.addTransactionHistory(amount, Directions.OUT.getCode(), desitnationAccount.getAccountId());
		
		desitnationAccount.deposit(amount);
		desitnationAccount.addTransactionHistory(amount, Directions.IN.getCode(), this.accountId);
		
		return desitnationAccount;
		
	}
	
	private void addTransactionHistory(int amount, String direction, String thirdPartyId) {
		Transaction transaction = new Transaction(); 
		transaction.setAmount(amount);
		transaction.setDirection(direction);
		transaction.setThirdPartyId(thirdPartyId);
		this.getTransactionHistory().add(transaction);
	}
	
	public List<Transaction> filterTransactionHistory(String direction){
		return this.getTransactionHistory().stream()
				.filter(t -> t.getDirection().equals(direction))
				.collect(Collectors.toList());
	}
	
	public Account(int balance) {
		super();
		this.balance = balance;
	}
	
	public Account(String accountId, int balance) {
		super();
		this.accountId = accountId;
		this.balance = balance;
	}
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public List<Transaction> getTransactionHistory() {
		return transactionHistory;
	}
	public int getBalance() {
		return balance;
	}
	
}
