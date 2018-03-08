package com.priceminister.account;


import static org.junit.Assert.*;

import org.junit.*;

import com.priceminister.account.implementation.*;


/**
 * Please create the business code, starting from the unit tests below.
 * Implement the first test, the develop the code that makes it pass.
 * Then focus on the second test, and so on.
 * 
 * We want to see how you "think code", and how you organize and structure a simple application.
 * 
 * When you are done, please zip the whole project (incl. source-code) and send it to recrutement-dev@priceminister.com
 * 
 */
public class CustomerAccountTest {
    
    Account customerAccount;
    AccountRule rule;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        customerAccount = new CustomerAccount();
    }
    
    /**
     * Tests that an empty account always has a balance of 0.0, not a NULL.
     */
    @Test
    public void testAccountWithoutMoneyHasZeroBalance() {
        Double balance=customerAccount.getBalance();
        Assert.assertEquals(0.0, emptyAccount);
        Assert.assertNotNull(emptyAccount);
    }
    
    /**
     * Adds money to the account and checks that the new balance is as expected.
     */
    @Test
    public void testAddPositiveAmount() {
        customerAccount.add(10);
        Assert.assertEquals(10.0, customerAccount.getBalance();
    }
    
    /**
     * Tests that an illegal withdrawal throws the expected exception.
     * Use the logic contained in CustomerAccountRule; feel free to refactor the existing code.
     */
    @Test
    public void testWithdrawAndReportBalanceIllegalBalance() {
        try {
            rule = new AccountRule();
            customerAccount.add(10);
            Double finalBalance = customerAccount.withdrawAndReportBalance(15.0, rule);
        } catch (final IllegalBalanceException e) {
            Assert.assertEquals("Illegal account balance: " + finalBalance, e.toString());
        }
    }
    
    // Also implement missing unit tests for the above functionalities.
    
    /**
     * Tests that a new account with money has a balance with the right initial amount
     */
    @Test
    public void testAccountWithoutMoneyHasZeroBalance() {
        CustomerAccount customerAccountWithMoney = new CustomerAccount(10.0);
        Double balance=customerAccount.getBalance();
        Assert.assertEquals(10.0, balance);
    }
    
    /**
     * Tests that a legal withdrawal gives the right balance
     */
    @Test
    public void testWithdrawAndReportBalanceLegalBalance() {
        try {
            customerAccount.add(20);
            rule = new AccountRule();
            Double finalBalance = customerAccount.withdrawAndReportBalance(15.0, rule);
            Assert.assertEquals(5.0, finalBalance);
        } catch (final IllegalBalanceException e) {}
    }
}
