package com.priceminister.account;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import com.priceminister.account.abstraction.Account;
import com.priceminister.account.abstraction.AccountRule;
import com.priceminister.account.exception.AccountRuleException;
import com.priceminister.account.exception.IllegalBalanceException;
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
    Account customerAccount;

    AccountRule accountRuleMock;

    double addedAmount = 10.7;
    double balance = 15.78;

    /**
     * SetUp initialising test objects
     */
    @Before
    public void setUp() {
        accountRuleMock = resultingAccountBalance -> false;
        customerAccount = new CustomerAccount();
    }

    /**
     * Tests that an empty account always has a balance of 0.0, not a NULL.
     */
    @Test
    public void testAccountWithoutMoneyHasZeroBalance() {
        assertThat("account without money and balance not 0 ", customerAccount.getBalance(), is(0.0));
    }

    /**
     * Adds money to the account and checks that the new balance is as expected.
     *
     * @throws IllegalBalanceException balance exception
     */
    @Test
    public void test_add_addedAmount_added() throws IllegalBalanceException {
        customerAccount.add(addedAmount);

        assertTrue(customerAccount.getBalance() > 0.0);
        assertThat("addition not applied on balance ", customerAccount.getBalance(), is(addedAmount));
    }

    /**
     * Tests that an illegal withdrawal throws the expected exception.
     * Use the logic contained in CustomerAccountRule; feel free to refactor the existing code.
     */
    @Test(expected = IllegalBalanceException.class)
    public void testWithdrawAndReportBalanceIllegalBalance() throws IllegalBalanceException, AccountRuleException {
        customerAccount.withdrawAndReportBalance(-1.1, accountRuleMock);
    }

    // Also implement missing unit tests for the above functionalities.
    /**
     * Tests that an AccountRuleException is thrown since the corresponding rule is null
     *
     * @throws IllegalBalanceException balance exception
     * @throws AccountRuleException    null account rule exception
     */
    @Test(expected = AccountRuleException.class)
    public void test_withdrawAndReportBalance_with_null_Rule_throwsAccountRuleException() throws IllegalBalanceException, AccountRuleException {
        customerAccount.withdrawAndReportBalance(1.0, null);
    }

    /**
     * Test adding a null amount throws an IllegalBalanceException.
     */
    @Test(expected = IllegalBalanceException.class)
    public void testAddNullAmount() throws IllegalBalanceException {
        customerAccount.add(null);
    }

    /**
     * Tests that setting a balance with a null amount throws an IllegalBalanceException.
     *
     * @throws IllegalBalanceException null balance exception
     */
    @Test(expected = IllegalBalanceException.class)
    public void test_SetNullBalance_throw_IllegalBalanceException() throws IllegalBalanceException {
        customerAccount.setBalance(null);
    }

    /**
     * Tests that setting a negative balance throws an IllegalBalanceException.
     *
     * @throws IllegalBalanceException null balance exception
     */
    @Test(expected = IllegalBalanceException.class)
    public void test_SetNegativeBalance_throw_IllegalBalanceException() throws IllegalBalanceException {
        customerAccount.setBalance(-1 * balance);
    }
}
