package com.priceminister.account.abstraction;

import com.priceminister.account.exception.AccountRuleException;
import com.priceminister.account.exception.IllegalBalanceException;

/**
 * This class represents a simple account.
 * It doesn't handle different currencies, all money is supposed to be of standard currency EUR.
 */
public interface Account {

    /**
     * Adds money to this account.
     *
     * @param addedAmount - the money to add
     */
    void add(Double addedAmount) throws IllegalBalanceException;

    /**
     * Withdraws money from the account.
     *
     * @param withdrawnAmount - the money to withdraw
     * @param rule            - the AccountRule that defines which balance is allowed for this account
     * @return the remaining account balance
     * @throws IllegalBalanceException if the withdrawal leaves the account with a forbidden balance
     */
    Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule) throws IllegalBalanceException, AccountRuleException;

    /**
     * Gets the current account balance.
     *
     * @return the account's balance
     */
    Double getBalance();

    /**
     * account balance Mutator.
     *
     * @param balance amount balance
     */
    void setBalance(Double balance) throws IllegalBalanceException;
}
