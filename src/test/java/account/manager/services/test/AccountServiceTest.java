package account.manager.services.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import account.manager.interfaces.IAccountService;
import account.manager.models.Account;
import account.manager.models.Directions;
import account.manager.models.Transaction;
import account.manager.services.AccountService;

public class AccountServiceTest {
	
	IAccountService accountService = new AccountService();
	
	@Test
	public void withdrawMustDoNothingGivenBalanceLowerThanAmount() {
		Account account = new Account(100);
		accountService.withdraw(account, 1000);
		assertEquals(100, account.getBalance());
	}
	
	@Test
	public void withdrawMustDoNothingGivenNegativeAmount() {
		Account account = new Account(100);
		accountService.withdraw(account, -100);
		assertEquals(100, account.getBalance());
	}
	
	@Test
	public void depositMustDoNothingGivenNegativeAmount() {
		Account account = new Account(100);
		accountService.deposit(account, -100);
		assertEquals(100, account.getBalance());
	}
	
	@Test
	public void withdrawMustReturnValidBalance() {
		Account account = new Account(100);
		accountService.withdraw(account, 60);
		assertEquals(40, account.getBalance());
	}

	@Test
	public void depositMustReturnValidBalance() {
		Account account = new Account(100);
		accountService.deposit(account, 60);
		assertEquals(160, account.getBalance());
	}
	
	@Test
	public void transerAmountMustDoNothingGivenNegativeAmount() {
		// if amount < 0 then do nothing
		Account account1 = new Account("account1", 100);
		Account account2 = new Account("account2", 100);
		accountService.transferAmount(account1, -100, account2);
		assertEquals(100, account1.getBalance());
		assertEquals(100, account2.getBalance());
	}

	@Test
	public void transerAmountMustDoNothingGivenBalanceLowerThanAmount() {
		// if amount > balance then do nothing.
		Account account1 = new Account("account1", 100);
		Account account2 = new Account("account2", 100);
		accountService.transferAmount(account1, 1000, account2);
		assertEquals(100, account1.getBalance());
		assertEquals(100, account2.getBalance());
	}
	
	@Test
	public void transferAmountMustReturnValidBalances() {
		Account account1 = new Account("account1", 100);
		Account account2 = new Account("account2", 100);
		
		account2 = accountService.transferAmount(account1, 60, account2);
		
		assertEquals(40, account1.getBalance());
		assertEquals(160, account2.getBalance());
		
	}

	
	@Test
	public void transactionHistoryMustReturnValidRecords(){
		Account account1 = new Account("account1", 100);
		Account account2 = new Account("account2", 100);
		
		accountService.transferAmount(account1, 60, account2);
		assertNotNull(account1.getTransactionHistory());
		assertEquals(account1.getTransactionHistory().size(), 1);
		assertEquals(account1.getTransactionHistory().get(0).getAmount(), 60);
		assertEquals(account1.getTransactionHistory().get(0).getDirection(), Directions.OUT.getCode());
		assertEquals(account1.getTransactionHistory().get(0).getThirdPartyId(), "account2");
		
		assertNotNull(account2.getTransactionHistory());
		assertEquals(account2.getTransactionHistory().size(), 1);
		assertEquals(account2.getTransactionHistory().get(0).getAmount(), 60);
		assertEquals(account2.getTransactionHistory().get(0).getDirection(), Directions.IN.getCode());
		assertEquals(account2.getTransactionHistory().get(0).getThirdPartyId(), "account1");
		
		accountService.transferAmount(account2, 40, account1);
		assertNotNull(account2.getTransactionHistory());
		assertEquals(account2.getTransactionHistory().size(), 2);
		assertEquals(account2.getTransactionHistory().get(1).getAmount(), 40);
		assertEquals(account2.getTransactionHistory().get(1).getDirection(), Directions.OUT.getCode());
		assertEquals(account2.getTransactionHistory().get(1).getThirdPartyId(), "account1");
		
		assertNotNull(account1.getTransactionHistory());
		assertEquals(account1.getTransactionHistory().size(), 2);
		assertEquals(account1.getTransactionHistory().get(1).getAmount(), 40);
		assertEquals(account1.getTransactionHistory().get(1).getDirection(), Directions.IN.getCode());
		assertEquals(account1.getTransactionHistory().get(1).getThirdPartyId(), "account2");
	}
	
	@Test
	public void filterTransactionHistoryMustReturnValidRecords(){
		Account account1 = new Account("account1", 100);
		Account account2 = new Account("account2", 100);
		accountService.transferAmount(account1, 60, account2);
		accountService.transferAmount(account2, 40, account1);
		
		List<Transaction> inTransactions = accountService.filterTransactionHistory(account1, Directions.IN.getCode());
		assertNotNull(inTransactions);
		assertEquals(inTransactions.size(), 1);
		assertEquals(inTransactions.get(0).getAmount(), 40);
		assertEquals(inTransactions.get(0).getDirection(), Directions.IN.getCode());
		assertEquals(inTransactions.get(0).getThirdPartyId(), "account2");
		
		List<Transaction> outTransactions = accountService.filterTransactionHistory(account1, Directions.OUT.getCode());
		assertNotNull(outTransactions);
		assertEquals(outTransactions.size(), 1);
		assertEquals(outTransactions.get(0).getAmount(), 60);
		assertEquals(outTransactions.get(0).getDirection(), Directions.OUT.getCode());
		assertEquals(outTransactions.get(0).getThirdPartyId(), "account2");
		
	}

}
