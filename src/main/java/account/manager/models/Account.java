package account.manager.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Account {
	private String accountId;
	private int balance;
	private List<Transaction> transactionHistory = new ArrayList<>();
	
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
	public void setBalance(int balance) {
		this.balance = balance;
	}
}
