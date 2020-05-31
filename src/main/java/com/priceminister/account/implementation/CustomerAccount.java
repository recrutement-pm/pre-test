package com.priceminister.account.implementation;

import com.priceminister.account.*;

import java.util.Optional;


public class CustomerAccount implements Account {

    private Double balance = 0.0d;

    public synchronized void add(Double addedAmount) throws AccountOperationConfigException {

        addedAmount = Optional.ofNullable(addedAmount).orElseThrow(() -> new AccountOperationConfigException(AccountOperationConfigException.NULL_ADDED_AMOUNT));

        if (addedAmount < 0) {
            throw new AccountOperationConfigException(AccountOperationConfigException.NEGATIVE_ADDED_AMOUNT);
        }

        balance = balance + addedAmount;
    }

    public Double getBalance() {
        return balance;
    }

    public synchronized Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule)
            throws IllegalBalanceException, AccountOperationConfigException {

        withdrawnAmount = Optional.ofNullable(withdrawnAmount).orElseThrow(() ->
                new AccountOperationConfigException(AccountOperationConfigException.NULL_WITHDRAWN_AMOUNT));

        if (withdrawnAmount < 0) {
            throw new AccountOperationConfigException(AccountOperationConfigException.NEGATIVE_WITHDRAWN_AMOUNT);
        }
        Double localBalance = balance;
        Double result = localBalance - withdrawnAmount;

        rule = Optional.ofNullable(rule).orElseThrow(() ->
                new AccountOperationConfigException(AccountOperationConfigException.THE_ACCOUNT_RULE_IS_NULL));

        if (rule.withdrawPermitted(result)) {
            this.balance = result;
        } else {
            throw new IllegalBalanceException(result);
        }

        return this.balance;
    }

}
