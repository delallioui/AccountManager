package account.manager.services;

import java.util.List;
import java.util.stream.Collectors;

import account.manager.interfaces.IAccountService;
import account.manager.models.Account;
import account.manager.models.Directions;
import account.manager.models.Transaction;

public class AccountService implements IAccountService {

	public void withdraw(Account account, int amount) {
		if(amount > account.getBalance() || amount < 0){
			return;
		}
		
		account.setBalance(account.getBalance() - amount);
		
	}

	public void deposit(Account account, int amount) {
		if(amount < 0){
			return;
		}
		
		account.setBalance(account.getBalance() + amount);
	}

	public Account transferAmount(Account account, int amount, Account desitnationAccount) {
		if(desitnationAccount == null){
			return null;
		}
		
		if(amount < 0 || amount > account.getBalance()){
			return desitnationAccount;
		}
		
		this.withdraw(account, amount);
		this.addTransactionHistory(account, amount, Directions.OUT.getCode(), desitnationAccount.getAccountId());
		
		this.deposit(desitnationAccount, amount);
		this.addTransactionHistory(desitnationAccount, amount, Directions.IN.getCode(), account.getAccountId());
		
		return desitnationAccount;
		
	}
	
	public List<Transaction> filterTransactionHistory(Account account, String direction){
		return account.getTransactionHistory().stream()
				.filter(t -> t.getDirection().equals(direction))
				.collect(Collectors.toList());
	}
	
	private void addTransactionHistory(Account account, int amount, String direction, String thirdPartyId) {
		Transaction transaction = new Transaction(amount, direction, thirdPartyId); 
		account.getTransactionHistory().add(transaction);
	}

}
