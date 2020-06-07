package com.priceminister.account.implementation;

import com.priceminister.account.*;


public class CustomerAccount implements Account {

    private Double currentBalance;

    public CustomerAccount() {
        currentBalance = 0.0d;
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
        if (!rule.withdrawPermitted(withdrawnAmount)) throw new IllegalBalanceException(withdrawResult);

        currentBalance -= withdrawnAmount;
        return getBalance();
    }
}
