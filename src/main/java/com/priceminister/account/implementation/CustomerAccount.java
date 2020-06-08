package com.priceminister.account.implementation;

import com.priceminister.account.Account;
import com.priceminister.account.AccountRule;
import com.priceminister.account.IllegalBalanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class CustomerAccount implements Account {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAccount.class);

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
            LOGGER.info(currentBalance + " has increased on " + addedAmount);
        }
    }

    public Double getBalance() {
        return currentBalance;
    }

    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule)
            throws IllegalBalanceException {
        if (getBalance() <= 0) {
            LOGGER.info("Withdraw is restrict. Get balance is negative or equals 0");
            throw new IllegalBalanceException(getBalance());
        }
        Double withdrawResult = getBalance() - withdrawnAmount;
        if (!rule.withdrawPermitted(withdrawResult)) {
            LOGGER.info("Withdraw is restrict. " + "Withdraw amount: " + withdrawnAmount +
                    " more than " + "current account balance " + getBalance());
            throw new IllegalBalanceException(withdrawResult);
        }

        currentBalance -= withdrawnAmount;
        return getBalance();
    }
}
