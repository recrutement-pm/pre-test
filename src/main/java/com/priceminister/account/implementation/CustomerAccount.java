package com.priceminister.account.implementation;

import com.priceminister.account.Account;
import com.priceminister.account.AccountRule;
import com.priceminister.account.IllegalBalanceException;

import java.util.UUID;


public class CustomerAccount implements Account {

    private UUID id;
    private Double currentBalance;

    public CustomerAccount() {
        currentBalance = 0.0d;
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void add(Double addedAmount) {
        if (addedAmount > 0) {
            currentBalance += addedAmount;
        }
    }

    public Double getBalance() {
        return currentBalance;
    }

    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule)
            throws IllegalBalanceException {
        if (getBalance() <= 0) throw new IllegalBalanceException(getBalance());

        Double withdrawResult = getBalance() - withdrawnAmount;
        if (!rule.withdrawPermitted(withdrawResult)) throw new IllegalBalanceException(withdrawResult);

        currentBalance -= withdrawnAmount;
        return getBalance();
    }
}
