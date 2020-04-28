package com.priceminister.account;

import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.priceminister.account.exception.IllegalBalanceException;
import com.priceminister.account.exception.InvalidSettingsException;
import com.priceminister.account.implementation.CustomerAccount;
import com.priceminister.account.implementation.CustomerAccountRule;
import com.priceminister.account.model.AccountSettings;
import com.priceminister.account.model.AccountSettings.AccountSettingsBuilder;
import com.priceminister.account.model.AccountType;

/**
 * Please create the business code, starting from the unit tests below.
 * Implement the first test, the develop the code that makes it pass. Then focus
 * on the second test, and so on.
 * 
 * We want to see how you "think code", and how you organize and structure a
 * simple application.
 * 
 * When you are done, please zip the whole project (incl. source-code) and send
 * it to recrutement-dev@priceminister.com
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
		final AccountSettings settings = new AccountSettingsBuilder().withAccountType(AccountType.BCA)
				.withMinPermittedBalance(10.0).withPeriodInDays(7).withMaxWithdrawelAmountPerPeriod(300.0).build();
		rule = new CustomerAccountRule(settings);
	}

	/**
	 * Tests that an empty account always has a balance of 0.0, not a NULL.
	 */
	@Test
	public void testAccountWithoutMoneyHasZeroBalance() {
		Assertions.assertThat(customerAccount.getBalance()).isNotNull();
	}

	/**
	 * Adds money to the account and checks that the new balance is as expected.
	 */
	@Test
	public void testAddPositiveAmount() {
		final Double initialBalance = customerAccount.getBalance();
		final Double amountToAdd = Math.random();
		customerAccount.add(amountToAdd);
		Assertions.assertThat(customerAccount.getBalance()).isEqualTo(initialBalance + amountToAdd);
	}

	/**
	 * Adds an invalid amount raises IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddInvalidAmount() {
		customerAccount.add(new Random().nextInt(1000) * -1.0);
	}

	/**
	 * Withdraw an invalid amount raises IllegalArgumentException
	 * 
	 * @throws IllegalBalanceException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWithdraInvalidAmount() throws IllegalBalanceException {
		customerAccount.withdrawAndReportBalance(new Random().nextInt(100) * -1.0, rule);
	}

	/**
	 * Tests that an illegal withdrawal throws the expected exception. Use the logic
	 * contained in CustomerAccountRule; feel free to refactor the existing code.
	 * 
	 * @throws IllegalBalanceException
	 * @throws InvalidSettingsException
	 */
	@Test(expected = IllegalBalanceException.class)
	public void testWithdrawAndReportBalanceIllegalBalance() throws IllegalBalanceException, InvalidSettingsException {
		customerAccount.add(200.0);
		customerAccount.withdrawAndReportBalance(300.0, rule);
	}

	/**
	 * Tests withdrawal with invalid inputs
	 * 
	 * @throws IllegalBalanceException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWithdrawAndReportBalanceIllegalArgument() throws IllegalBalanceException {
		customerAccount.withdrawAndReportBalance(Math.random(), null);
	}

	/**
	 * Tests withdrawal and check new balance
	 * 
	 * @throws IllegalBalanceException
	 * @throws InvalidSettingsException
	 */
	@Test
	public void testWithdrawAndReportBalanceSuccess() throws IllegalBalanceException, InvalidSettingsException {
		customerAccount.add(1000.0);
		Double withdrawnAmount = 200.0;
		Double oldBalance = customerAccount.getBalance();
		Assertions.assertThat(withdrawnAmount).isLessThanOrEqualTo(rule.getSettings().getMaxWithdrawnAmountPerPeriod());
		Assertions.assertThat(oldBalance - withdrawnAmount)
				.isGreaterThanOrEqualTo(rule.getSettings().getMinPermittedBalance());
		customerAccount.withdrawAndReportBalance(withdrawnAmount, rule);
		Assertions.assertThat(customerAccount.getBalance()).isEqualTo(oldBalance - withdrawnAmount);
	}

}
