package com.priceminister.account.implementation;

import com.priceminister.account.*;


public class CustomerAccount implements Account {

    private Double balance = 0.0;

    public void add(Double addedAmount) throws IllegalAmountException {
        if(addedAmount == null || addedAmount < 0) {
            throw new IllegalAmountException(addedAmount);
        }
        this.balance += addedAmount;
    }

    public Double getBalance() {
        return this.balance;
    }

    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule) 
    		throws IllegalBalanceException {
        double tempBalance = this.balance - withdrawnAmount;
        if(!rule.withdrawPermitted(tempBalance)) {
            throw new IllegalBalanceException(tempBalance);
        }
        this.balance = tempBalance;
        return this.balance ;
    }

}
