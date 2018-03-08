package com.priceminister.account.implementation;

import com.priceminister.account.*;


public class CustomerAccount implements Account {
    
    public Double balance;
    
    public void add(Double addedAmount) {
        this.balance += addedAmount;
    }

    public Double getBalance() {
        return this.balance;
    }

    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule) {
        Double finalBalance = this.balance-withdrawnAmount;
        if(rule.withdrawPermitted(finalBalance)) {
            return finalBalance;
        } else {
            throw new IllegalBalanceException(finalBalance);
        }
    }

    public CustomerAccount() {
        this.balance = 0;
    }
    public CustomerAccount(Double initalAmount) {
        this.balance = initalAmount;
    }
}
