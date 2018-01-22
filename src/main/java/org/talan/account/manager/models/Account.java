package org.talan.account.manager.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
	private String accountId;
	private Float balance;
	private List<Transaction> transactionHistory = new ArrayList<>();
	
	public Account(String accountId, Float balance) {
		super();
		this.accountId = accountId;
		this.balance = balance;
	}
	
}
