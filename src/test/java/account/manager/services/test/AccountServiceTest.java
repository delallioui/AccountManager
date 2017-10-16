package account.manager.services.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import account.manager.models.Account;
import account.manager.models.Directions;
import account.manager.models.Transaction;

public class AccountServiceTest {

	@Test
	public void withdrawMustReturnValidBalance() {
		Account account = new Account(100);
		account.withdraw(60);
		assertEquals(40, account.getBalance());
		
		// if amount > balance then do nothing.
		account.withdraw(1000);
		assertEquals(40, account.getBalance());
		
		// if amount < 0 then do nothing
		account.withdraw(-100);
		assertEquals(40, account.getBalance());
	}

	@Test
	public void depositMustReturnValidBalance() {
		Account account = new Account(100);
		account.deposit(60);
		assertEquals(160, account.getBalance());
		
		// if amount < 0 then do nothing
		account.withdraw(-100);
		assertEquals(160, account.getBalance());
	}
	
	@Test
	public void transferAmountMustReturnValidBalances() {
		Account account1 = new Account("account1", 100);
		Account account2 = new Account("account2", 100);
		
		account2 = account1.transferAmount(60, account2);
		
		assertEquals(40, account1.getBalance());
		assertEquals(160, account2.getBalance());
		
		// if amount > balance then do nothing.
		account1.transferAmount(1000, account2);
		assertEquals(40, account1.getBalance());
		assertEquals(160, account2.getBalance());
		
		// if amount < 0 then do nothing
		account1.transferAmount(-100, account2);
		assertEquals(40, account1.getBalance());
		assertEquals(160, account2.getBalance());
	}
	
	@Test
	public void transactionHistoryMustReturnValidRecords(){
		Account account1 = new Account("account1", 100);
		Account account2 = new Account("account2", 100);
		
		account1.transferAmount(60, account2);
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
		
		account2.transferAmount(40, account1);
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
		account1.transferAmount(60, account2);
		account2.transferAmount(40, account1);
		
		List<Transaction> inTransactions = account1.filterTransactionHistory(Directions.IN.getCode());
		assertNotNull(inTransactions);
		assertEquals(inTransactions.size(), 1);
		assertEquals(inTransactions.get(0).getAmount(), 40);
		assertEquals(inTransactions.get(0).getDirection(), Directions.IN.getCode());
		assertEquals(inTransactions.get(0).getThirdPartyId(), "account2");
		
		List<Transaction> outTransactions = account1.filterTransactionHistory(Directions.OUT.getCode());
		assertNotNull(outTransactions);
		assertEquals(outTransactions.size(), 1);
		assertEquals(outTransactions.get(0).getAmount(), 60);
		assertEquals(outTransactions.get(0).getDirection(), Directions.OUT.getCode());
		assertEquals(outTransactions.get(0).getThirdPartyId(), "account2");
		
	}

}
