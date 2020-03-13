package com.priceminister.account.launch;

import com.priceminister.account.exception.AccountRuleException;
import com.priceminister.account.exception.IllegalBalanceException;
import com.priceminister.account.implementation.CustomerAccount;
import com.priceminister.account.implementation.CustomerAccountRule;

public class Main {
    static CustomerAccount customerAccount = new CustomerAccount();
    static CustomerAccountRule customerAccountRule = new CustomerAccountRule();

    public static void main(String[] args){
        try {
            init();
            launchOperation(15.78, 12.59);
            System.out.print(customerAccount.getBalance());
        } catch (IllegalBalanceException | AccountRuleException e) {
            e.printStackTrace();
        }
    }

    private static void init() {
        customerAccount = new CustomerAccount();
        customerAccountRule = new CustomerAccountRule();
    }

    private static void launchOperation(double balance, double withDrawAmount) throws IllegalBalanceException, AccountRuleException {
        customerAccount.add(balance);
        customerAccount.withdrawAndReportBalance(withDrawAmount, customerAccountRule);


    }
}
