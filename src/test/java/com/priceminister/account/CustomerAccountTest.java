package com.priceminister.account;


import static org.junit.Assert.*;

import org.junit.*;

import com.priceminister.account.implementation.*;


/**
 * Please create the business code, starting from the unit tests below.
 * Implement the first test, the develop the code that makes it pass.
 * Then focus on the second test, and so on.
 * <p>
 * We want to see how you "think code", and how you organize and structure a simple application.
 * <p>
 * When you are done, please zip the whole project (incl. source-code) and send it to recrutement-dev@priceminister.com
 */
public class CustomerAccountTest {

    private Account customerAccount;
    private AccountRule rule;

    /**
     *
     */
    @Before
    public void setUp() {
        customerAccount = new CustomerAccount();
        rule = new CustomerAccountRule();
    }

    /**
     * Tests that an empty account always has a balance of 0.0, not a NULL.
     */
    @Test
    public void testAccountWithoutMoneyHasZeroBalance() {
        Double balance = customerAccount.getBalance();
        Assert.assertEquals(balance, Double.valueOf(0.0));
    }

    /**
     * Adds money to the account and checks that the new balance is as expected.
     */
    @Test
    public void testAddPositiveAmount() {
        Double balance = customerAccount.getBalance();
        try {
            customerAccount.add(10d);
        } catch (AccountOperationConfigException e) {
            fail();
        }
        Double newBalance = customerAccount.getBalance();
        Assert.assertEquals(Double.valueOf(balance + 10d), newBalance);
    }

    /**
     * test illegal add of negative amount
     * @throws AccountOperationConfigException
     */
    @Test(expected = AccountOperationConfigException.class)
    public void testAddNegativeAmount() throws AccountOperationConfigException {
        try {
            customerAccount.add(-10d);
        } catch (AccountOperationConfigException e) {
            Assert.assertEquals(AccountOperationConfigException.NEGATIVE_ADDED_AMOUNT, e.toString());
            throw e;
        }
    }

    /**
     * test illegal add of null amount
     * @throws AccountOperationConfigException
     */
    @Test(expected = AccountOperationConfigException.class)
    public void testAddNullAmount() throws AccountOperationConfigException {
        try {
            customerAccount.add(null);
        } catch (AccountOperationConfigException e) {
            Assert.assertEquals(AccountOperationConfigException.NULL_ADDED_AMOUNT, e.toString());
            throw e;
        }
    }

    /**
     * Tests that an illegal withdrawal throws the expected exception.
     * Use the logic contained in CustomerAccountRule; feel free to refactor the existing code.
     */
    @Test(expected = IllegalBalanceException.class)
    public void testWithdrawAndReportBalanceIllegalBalance() throws IllegalBalanceException {

        Double withdrawnAmount = customerAccount.getBalance() + 10d;

        try {
            customerAccount.withdrawAndReportBalance(withdrawnAmount, rule);
        } catch (AccountOperationConfigException AccountOperationConfigException) {
            fail();
        }
    }

    /**
     * Tests that a null withdrawn amount throws the expected exception.
     */
    @Test(expected = AccountOperationConfigException.class)
    public void testWithdrawAndReportWithdrawConfigExceptionWhenAmountIsNull() throws AccountOperationConfigException {
        try {
            customerAccount.withdrawAndReportBalance(null, rule);
        } catch (IllegalBalanceException e) {
            fail();
        } catch (AccountOperationConfigException e) {
            Assert.assertEquals(AccountOperationConfigException.NULL_WITHDRAWN_AMOUNT, e.toString());
            throw e;
        }
    }

    /**
     * Tests that a null withdrawn amount throws the expected exception.
     */
    @Test(expected = AccountOperationConfigException.class)
    public void testWithdrawAndReportWithdrawConfigExceptionWhenAmountIsNegative() throws AccountOperationConfigException {
        try {
            customerAccount.withdrawAndReportBalance(-10d, rule);
        } catch (IllegalBalanceException e) {
            fail();
        } catch (AccountOperationConfigException e) {
            Assert.assertEquals(AccountOperationConfigException.NEGATIVE_WITHDRAWN_AMOUNT, e.toString());
            throw e;
        }
    }

    /**
     * Tests that a withdrawal operation with null account rule, throws the expected exception.
     */
    @Test(expected = AccountOperationConfigException.class)
    public void testWithdrawAndReportWithdrawConfigExceptionWhenAccountRuleIsNull() throws AccountOperationConfigException {
        try {
            customerAccount.withdrawAndReportBalance(10d, null);
        } catch (IllegalBalanceException e) {
            fail();
        } catch (AccountOperationConfigException e) {
            Assert.assertEquals(AccountOperationConfigException.THE_ACCOUNT_RULE_IS_NULL, e.toString());
            throw e;
        }
    }

    @Test
    public void testWithdrawAndReportBalanceLegalBalance() {
        try {
            customerAccount.add(50d);
        } catch (AccountOperationConfigException e) {
            fail();
        }
        Double balance = null;
        try {
            balance = customerAccount.withdrawAndReportBalance(10d, rule);
        } catch (IllegalBalanceException | AccountOperationConfigException e) {
            fail();
        }
        Assert.assertEquals(balance, Double.valueOf(40));
    }

    @Test
    public void testConcurrentOperationOnSameAccount() throws InterruptedException {
        try {
            customerAccount.add(20d);
        } catch (AccountOperationConfigException e) {
            fail();
        }
        Runnable runnable = () -> {
            try {
                customerAccount.withdrawAndReportBalance(10d, rule);
            } catch (AccountOperationConfigException | IllegalBalanceException withdrawError) {
                fail();
            }
        };

        Runnable runnable2 = () -> {
            try {
                customerAccount.withdrawAndReportBalance(10d, rule);
            } catch (AccountOperationConfigException | IllegalBalanceException withdrawError) {
                fail();
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        Assert.assertEquals(Double.valueOf(0), customerAccount.getBalance());
    }

}
