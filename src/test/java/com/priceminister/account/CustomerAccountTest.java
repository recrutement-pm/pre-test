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
        rule = new CustomerAccountRule();
        CustomerAccount.balance = 0.0;
    }
    
    /**
     * Tests that an empty account always has a balance of 0.0, not a NULL.
     */
    @Test
    public void testAccountWithoutMoneyHasZeroBalance() {
        Assert.assertNotNull(customerAccount.getBalance());
        Assert.assertEquals(new Double(0),customerAccount.getBalance());
    }
    
    /**
     * Adds money to the account and checks that the new balance is as expected.
     */
    @Test
    public void testAddPositiveAmount() {
        Double balance = customerAccount.getBalance();
        //check before
        Assert.assertNotNull(balance);
        //add 10 to balance
        customerAccount.add(10.0);
        //check after
        Assert.assertEquals(Double.valueOf(10.0), customerAccount.getBalance());
    }
    
    /**
     * Tests that an illegal withdrawal throws the expected exception.
     */
    @Test
    public void testWithdrawAndReportBalanceIllegalBalance() {
        try {
            customerAccount.withdrawAndReportBalance(Double.valueOf(20), rule);
            fail();
        } catch (IllegalBalanceException e) {
            assertEquals("Illegal account balance: -20.0", e.toString());
        }
    }

    /**
     * Tests that withdrawal change the balance.
     */
    @Test
    public void testWithdrawOk() throws Exception{
        Double balance = customerAccount.getBalance();
        //check before
        Assert.assertNotNull(balance);
        //add 10 to balance
        customerAccount.add(10.0);
        Double newBalance = customerAccount.withdrawAndReportBalance(Double.valueOf(5), rule);
        assertEquals(customerAccount.getBalance(), newBalance);
    }
}
