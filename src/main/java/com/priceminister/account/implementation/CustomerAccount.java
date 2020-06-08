package com.priceminister.account.implementation;

import com.priceminister.account.Account;
import com.priceminister.account.AccountRule;
import com.priceminister.account.IllegalBalanceException;
import com.priceminister.account.NegativeAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerAccount implements Account {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAccount.class);

    private volatile Double currentBalance;
    private final static Object MONITOR = new Object();

    public CustomerAccount() {
        currentBalance = 0.0d;
    }

    public void add(Double addedAmount) throws NegativeAmountException {
        synchronized (MONITOR) {
            if (addedAmount >= 0) {
                currentBalance += addedAmount;
                LOGGER.info(currentBalance + " has increased on " + addedAmount);
            } else {
                LOGGER.error(addedAmount + " is not valid");
                throw new NegativeAmountException("Amount to add cannot be less than 0");
            }
        }
    }

    public Double getBalance() {
        return currentBalance;
    }

    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule)
            throws IllegalBalanceException {
        synchronized (MONITOR) {
            Double withdrawResult = getBalance() - withdrawnAmount;
            if (!rule.withdrawPermitted(withdrawResult)) {
                LOGGER.info("Withdraw is restrict. " + "Withdraw amount: " + withdrawnAmount +
                        " more than " + "current account balance " + getBalance());
                throw new IllegalBalanceException(withdrawResult);
            }
            currentBalance = withdrawResult;
            return withdrawResult;
        }
    }
}
