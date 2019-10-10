package com.priceminister.account.implementation;

import com.priceminister.account.*;


public class CustomerAccount implements Account {

    public static Double balance;

    /**
     * Add amount to balance
     * @param addedAmount - the money to add
     */
    public void add(Double addedAmount) {
        if(addedAmount > 0)
        balance = Double.valueOf(balance + addedAmount);
    }

    /**
     * Get balance
     * @return balance
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * Withdraw and report balance
     * @param withdrawnAmount - the money to withdraw
     * @param rule - the AccountRule that defines which balance is allowed for this account
     * @return
     * @throws IllegalBalanceException
     */
    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule) 
    		throws IllegalBalanceException {
        Double balanceAfterWithdraw = balance - withdrawnAmount;
        if(!rule.withdrawPermitted(balanceAfterWithdraw)) throw new IllegalBalanceException(balanceAfterWithdraw);
        balance = Double.valueOf(balanceAfterWithdraw);
        return balance;
    }

}
