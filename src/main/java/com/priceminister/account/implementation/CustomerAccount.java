package com.priceminister.account.implementation;

import com.priceminister.account.abstraction.Account;
import com.priceminister.account.abstraction.AccountRule;
import com.priceminister.account.exception.AccountRuleException;
import com.priceminister.account.exception.IllegalBalanceException;


/**
 * Class implementing an account @link{{@link Account}}
 */
public class CustomerAccount implements Account {

    private Double balance = 0.0;

    @Override
    public void add(Double addedAmount) throws IllegalBalanceException {
        if (addedAmount == null)
            throw new IllegalBalanceException(addedAmount);
        setBalance(addedAmount);
    }

    @Override
    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule) throws IllegalBalanceException, AccountRuleException {
        if (rule == null)
            throw new AccountRuleException();

        if (getBalance() < withdrawnAmount)
            throw new IllegalBalanceException("balance lower than withdrawnAmount");
        if (!rule.withdrawPermitted(withdrawnAmount))
            throw new IllegalBalanceException(withdrawnAmount);

        this.setBalance(withdrawnAmount);

        return getBalance();
    }

    @Override
    public Double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(Double balance) throws IllegalBalanceException {
        if (balance == null)
            throw new IllegalBalanceException("balance cannot be null");

        if (balance < 0)
            throw new IllegalBalanceException("balance cannot be a negative value");

        this.balance = balance;
    }

}
