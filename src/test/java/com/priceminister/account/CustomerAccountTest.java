package com.priceminister.account;


import static org.junit.Assert.*;
import org.junit.*;

import com.priceminister.account.implementation.*;
import org.junit.rules.ExpectedException;


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
    AccountRule rule = new CustomerAccountRule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
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

        assertEquals(new Double(0) , customerAccount.getBalance() );

    }
    
    /**
     * Adds money to the account and checks that the new balance is as expected.
     */
    @Test
    public void testAddPositiveAmount() throws IllegalAmountException {
        customerAccount.add(100.0);
        assertEquals(new Double(100) , customerAccount.getBalance() );
    }

    /**
     * Tests that adding negative amount throws the expected exception.
     */
    @Test
    public void testAddNegativeAmountIllegalAmount() throws IllegalAmountException {
        exceptionRule.expect(IllegalAmountException.class);
        exceptionRule.expectMessage("Illegal amount: -50.0");
        customerAccount.add(-50.0);
    }

    /**
     * Tests that adding null amount throws the expected exception.
     */
    @Test
    public void testAddNullAmountIllegalAmount() throws IllegalAmountException {
        exceptionRule.expect(IllegalAmountException.class);
        exceptionRule.expectMessage("Illegal amount: null");
        customerAccount.add(null);
    }
    
    /**
     * Tests that an illegal withdrawal throws the expected exception.
     * Use the logic contained in CustomerAccountRule; feel free to refactor the existing code.
     */
    @Test
    public void testWithdrawAndReportBalanceIllegalBalance() throws IllegalBalanceException {
        exceptionRule.expect(IllegalBalanceException.class);
        exceptionRule.expectMessage("Illegal account balance: -50.0");

        customerAccount.withdrawAndReportBalance(50.0, rule );

    }

    /**
     * Tests that an illegal withdrawal throws the expected exception.
     * Use the logic contained in CustomerAccountRule; feel free to refactor the existing code.
     */
    @Test
    public void testWithdrawAndReportBalanceIllegalBalanceWhenZero() throws IllegalBalanceException {
        exceptionRule.expect(IllegalBalanceException.class);
        exceptionRule.expectMessage("Illegal account balance: 0.0");

        customerAccount.withdrawAndReportBalance(0.0, rule );

    }

    @Test
    public void testWithdrawAndReportBalance() throws IllegalBalanceException, IllegalAmountException {
        customerAccount.add(60.0);
        Double balance = customerAccount.withdrawAndReportBalance(50.0, rule);
        assertEquals(new Double(100),  balance);
    }



}
