package com.priceminister.account.implementation;

import java.time.LocalDateTime;
import java.util.UUID;


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

    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule) 
    		throws IllegalBalanceException {
        // TODO Auto-generated method stub
        return null;
    }

}
