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
    }
    
    /**
     * Tests that an empty account always has a balance of 0.0, not a NULL.
     */
    @Test
    public void testAccountWithoutMoneyHasZeroBalance() throws IllegalBalanceException {
        // arrange
        Double expectedResult = 0.0d;

        // act
        Double result = customerAccount.getBalance();

        // assert
        Assert.assertEquals(result, expectedResult);
    }
    
    /**
     * Adds money to the account and checks that the new balance is as expected.
     */
    @Test
    public void testAddPositiveAmount() {
        // arrange
        Double oldBalance = customerAccount.getBalance();
        Double addedAmount = 10.0d;

        // act
        customerAccount.add(addedAmount);
        Double expectedResult = oldBalance + addedAmount;

        // assert
        Assert.assertEquals(expectedResult, customerAccount.getBalance());
    }
    
    /**
     * Tests that an illegal withdrawal throws the expected exception.
     * Use the logic contained in CustomerAccountRule; feel free to refactor the existing code.
     */
    @Test
    public void testWithdrawAndReportBalanceIllegalBalance() {
        fail("not yet implemented");
    }
    
    // Also implement missing unit tests for the above functionalities.

}
