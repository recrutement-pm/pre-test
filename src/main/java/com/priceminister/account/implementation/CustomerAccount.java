package com.priceminister.account.implementation;

import com.priceminister.account.*;


public class CustomerAccount implements Account {

    private Double currentBalance;
    public CustomerAccount(){
        currentBalance = 0.0d;
    }

    public void add(Double addedAmount) {
        // TODO Auto-generated method stub
    }

    public Double getBalance() {
        return currentBalance;
    }

    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule) 
    		throws IllegalBalanceException {
        // TODO Auto-generated method stub
        return null;
    }

}
