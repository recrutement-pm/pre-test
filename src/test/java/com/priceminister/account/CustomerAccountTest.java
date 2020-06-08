package com.priceminister.account;


import com.priceminister.account.implementation.CustomerAccount;
import com.priceminister.account.implementation.CustomerAccountRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


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
    public void testAddPositiveAmount() throws NegativeAmountException {
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
     * Adds negative amount will throw exception
     */
    @Test(expected = NegativeAmountException.class)
    public void addNegativeNumberWillNotChangeBalance() throws NegativeAmountException {
        // arrange
        Double oldBalance = customerAccount.getBalance();
        Double negativeAmount = -10.0d;
        Double expectedResult = oldBalance;

        // act
        customerAccount.add(negativeAmount);
    }

    /**
     * Tests that an illegal withdrawal throws the expected exception.
     * Use the logic contained in CustomerAccountRule; feel free to refactor the existing code.
     */
    @Test(expected = IllegalBalanceException.class)
    public void testWithdrawAndReportBalanceWillThrowIllegalBalanceIfCurrentBalanceIsZero()
            throws IllegalBalanceException {
        // arrange
        Double withdrawnAmount = 10.0d;

        // act
        Double result = customerAccount.withdrawAndReportBalance(withdrawnAmount, rule);
    }

    @Test(expected = IllegalBalanceException.class)
    public void testWithdrawAndReportBalanceWillThrowIllegalBalanceIfCurrentBalanceLessThenWithdrawnAmount()
            throws IllegalBalanceException, NegativeAmountException {
        // arrange
        Double addedAmount = 9.0d;
        customerAccount.add(addedAmount);
        Double withdrawnAmount = 10.0d;

        // act
        Double result = customerAccount.withdrawAndReportBalance(withdrawnAmount, rule);
    }

    @Test
    public void testWithdrawAndReportBalanceWillChangeCurrentBalance()
            throws IllegalBalanceException, NegativeAmountException {
        // arrange
        Double addedAmount = 9.0d;
        customerAccount.add(addedAmount);
        Double withdrawnAmount = 8.0d;
        Double oldBalance = customerAccount.getBalance();
        Double expectedBalance = oldBalance - withdrawnAmount;

        // act
        Double result = customerAccount.withdrawAndReportBalance(withdrawnAmount, rule);

        // assert
        Assert.assertEquals(expectedBalance, result);
    }

    @Test
    public void testWithdrawAmountByMoreThenOneThread() throws IllegalBalanceException, NegativeAmountException, InterruptedException {
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        Double withdrawnAmount = 10.0d;
        customerAccount.add(100.0d);
        Double expectedBalance = 80.0d;
        IntStream.range(0, 2)
                .forEach(count -> service.submit(() -> customerAccount.withdrawAndReportBalance(withdrawnAmount, rule)));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        Assert.assertEquals(expectedBalance, customerAccount.getBalance());
    }
}
