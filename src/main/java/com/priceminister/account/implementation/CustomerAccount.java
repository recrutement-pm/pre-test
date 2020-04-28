package com.priceminister.account.implementation;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import com.priceminister.account.Account;
import com.priceminister.account.AccountRule;
import com.priceminister.account.exception.IllegalBalanceException;
import com.priceminister.account.model.AccountSettings;
import com.priceminister.account.model.MoneyTransaction;
import com.priceminister.account.model.Operation;
import com.priceminister.account.model.OperationType;

public class CustomerAccount implements Account {
	private final String number;
	private Double balance = 0.0;
	private AccountSettings accountSettings;
	private MutableList<Operation> operations = Lists.mutable.empty();

	public CustomerAccount() {
		this.number = UUID.randomUUID().toString();
		operations = Lists.mutable.of(new Operation(LocalDateTime.now(), OperationType.CREATION));
	}

	public CustomerAccount(Double balance, AccountSettings accountSettings) {
		this.number = UUID.randomUUID().toString();
		this.balance = balance;
		this.accountSettings = accountSettings;
		operations = Lists.mutable.of(new Operation(LocalDateTime.now(), OperationType.CREATION));
	}

	public String getNumber() {
		return number;
	}

	public AccountSettings getAccountSettings() {
		return accountSettings;
	}

	public MutableList<Operation> getOperations() {
		return operations;
	}

	public Double getBalance() {
		return this.balance;
	}

	public void add(final Double addedAmount) {
		if (addedAmount < 0) {
			throw new IllegalArgumentException("addedAmount");
		}
		this.balance = this.balance + addedAmount;
		Operation deposit = new MoneyTransaction(LocalDateTime.now(), OperationType.DEPOSIT, addedAmount, this.balance,
				"ATM");
		operations.add(deposit);
	}

	public Double withdrawAndReportBalance(final Double withdrawnAmount, final AccountRule rule)
			throws IllegalBalanceException {

		verifyWithdrawalInputs(withdrawnAmount, rule);

		boolean permitted = rule.withdrawPermitted(withdrawnAmount, balance,
				getLastOperationByType(OperationType.WITHDRAWAL));

		if (!permitted) {
			throw new IllegalBalanceException(balance - withdrawnAmount);
		}

		this.balance = this.balance - withdrawnAmount;
		Operation withdrawal = new MoneyTransaction(LocalDateTime.now(), OperationType.WITHDRAWAL, withdrawnAmount,
				this.balance, "ATM");
		operations.add(withdrawal);

		return balance;
	}

	private void verifyWithdrawalInputs(final Double withdrawnAmount, final AccountRule rule) {
		if (rule == null) {
			throw new IllegalArgumentException("rule");
		}

		if (withdrawnAmount < 0) {
			throw new IllegalArgumentException("withdrawnAmount");
		}
	}

	public Optional<MoneyTransaction> getLastOperationByType(final OperationType operationType) {
		if (this.operations.isEmpty()) {
			return Optional.empty();
		}
		return Optional.ofNullable((MoneyTransaction) this.operations
				.select(operation -> operation.getType().equals(operationType)).getLast());
	}

}
