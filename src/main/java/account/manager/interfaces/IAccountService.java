package account.manager.interfaces;

import java.util.List;

import account.manager.models.Account;
import account.manager.models.Transaction;

public interface IAccountService {

	void withdraw(Account account, int amount);
	
	void deposit(Account account, int amount);
	
	Account transferAmount(Account account, int amount, Account desitnationAccount);
	
	List<Transaction> filterTransactionHistory(Account account, String direction);
}
