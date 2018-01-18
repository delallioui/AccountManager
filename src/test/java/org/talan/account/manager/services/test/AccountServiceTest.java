package org.talan.account.manager.services.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;
import org.talan.account.manager.Exceptions.OperationException;
import org.talan.account.manager.Exceptions.TransferException;
import org.talan.account.manager.dto.OperationDto;
import org.talan.account.manager.dto.TransactionDto;
import org.talan.account.manager.dto.TransactionFilterDto;
import org.talan.account.manager.models.Account;
import org.talan.account.manager.models.Directions;
import org.talan.account.manager.service.AccountService;

public class AccountServiceTest {

	public static final String accountId = "acc1";
	public static final String accountId2 = "acc2";
	AccountService accountService = new AccountService();

	@Before
	public void init() {
		Account account = new Account(accountId, 100F);
		Account account2 = new Account(accountId2, 100F);
		accountService.createAccount(account);
		accountService.createAccount(account2);
	}

	@Test
	public void withdrawMustDoNothingGivenBalanceLowerThanAmount() {

		assertThatThrownBy(() -> {
			accountService.withdraw(new OperationDto(accountId, 1000F));
		}).isInstanceOf(OperationException.class);

		// assertEquals(100, account.getBalance());
	}

	@Test
	public void withdrawMustDoNothingGivenNegativeAmount() throws Exception {

		assertThatThrownBy(() -> {
			accountService.withdraw(new OperationDto(accountId, -100F));
		}).isInstanceOf(OperationException.class);
	}

	@Test
	public void depositMustDoNothingGivenNegativeAmount() {

		assertThatThrownBy(() -> {
			accountService.deposit(new OperationDto(accountId, -100F));
		}).isInstanceOf(OperationException.class);
	}

	@Test
	public void withdrawMustReturnValidBalance() throws Exception {
		boolean result = accountService.withdraw(new OperationDto(accountId, 60F));
		assertThat(result).isEqualTo(true);
		assertThat(accountService.findAccount(accountId).getBalance()).isEqualTo(40F);
	}

	@Test
	public void depositMustReturnValidBalance() throws OperationException {
		boolean result = accountService.deposit(new OperationDto(accountId, 60F));
		assertThat(result).isEqualTo(true);
		assertThat(accountService.findAccount(accountId).getBalance()).isEqualTo(160F);
	}

	@Test
	public void transerAmountMustDoNothingGivenNegativeAmount() {
		assertThatThrownBy(() -> {
			accountService.transferAmount(new TransactionDto(accountId, -100F, accountId2));
		}).isInstanceOf(TransferException.class);
	}

	@Test
	public void transerAmountMustDoNothingGivenBalanceLowerThanAmount() {
		assertThatThrownBy(() -> {
			accountService.transferAmount(new TransactionDto(accountId, 1000F, accountId2));
		}).isInstanceOf(TransferException.class);
	}

	@Test
	public void transferAmountMustReturnValidBalances() throws Exception {
		boolean result = accountService.transferAmount(new TransactionDto(accountId, 60F, accountId2));

		assertThat(result).isEqualTo(true);
		assertThat(accountService.findAccount(accountId).getBalance()).isEqualTo(40F);
		assertThat(accountService.findAccount(accountId2).getBalance()).isEqualTo(160F);
	}

	@Test
	public void transactionHistoryMustReturnValidRecords() throws Exception {
		boolean result = accountService.transferAmount(new TransactionDto(accountId, 60F, accountId2));
		
		assertThat(accountService.findAccount(accountId).getTransactionHistory()).isNotNull();
		assertThat(accountService.findAccount(accountId).getTransactionHistory().size()).isEqualTo(1);
	}

	@Test
	public void filterTransactionHistoryMustReturnValidRecords() throws Exception {
		accountService.transferAmount(new TransactionDto(accountId, 60F, accountId2));
		
		assertThat(accountService.filterTransactionHistory(new TransactionFilterDto(accountId, Directions.OUT))).isNotNull();
		assertThat(accountService.filterTransactionHistory(new TransactionFilterDto(accountId, Directions.OUT)).size()).isEqualTo(1);
		assertThat(accountService.filterTransactionHistory(new TransactionFilterDto(accountId, Directions.OUT)).get(0).getDirection()).isEqualTo(Directions.OUT.getCode());
		
	}

}