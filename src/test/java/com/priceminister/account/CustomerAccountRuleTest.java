package com.priceminister.account;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.priceminister.account.implementation.CustomerAccountRule;
import com.priceminister.account.model.AccountSettings;
import com.priceminister.account.model.AccountSettings.AccountSettingsBuilder;
import com.priceminister.account.model.AccountType;
import com.priceminister.account.model.MoneyTransaction;
import com.priceminister.account.model.OperationType;

public class CustomerAccountRuleTest {

	AccountRule rule;
	Double withdrawalAllowed;
	Double currentBalance;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		final AccountSettings settings = new AccountSettingsBuilder().withAccountType(AccountType.BCA)
				.withMinPermittedBalance(100.0).withPeriodInDays(7).withMaxWithdrawelAmountPerPeriod(300.0).build();
		rule = new CustomerAccountRule(settings);
		currentBalance = 1000.0;
		withdrawalAllowed = 200.0;
		Assertions.assertThat(withdrawalAllowed)
				.isLessThanOrEqualTo(rule.getSettings().getMaxWithdrawnAmountPerPeriod());
	}

	/**
	 * Tests that a withdrawal is not permitted when withdrawn amount exceeds
	 * maximum allowed.
	 */
	@Test
	public void withdrawNotPermittedWhenAmountTooBig() {
		Double highWithdrawnAmount = 500.0;
		Assertions.assertThat(currentBalance - highWithdrawnAmount)
				.isGreaterThanOrEqualTo(rule.getSettings().getMinPermittedBalance());
		boolean permitted = rule.withdrawPermitted(highWithdrawnAmount, currentBalance, Optional.empty());
		Assertions.assertThat(permitted).isFalse();
	}

	/**
	 * Tests that a withdrawal is not permitted when new balance is less than
	 * minimum permitted balance.
	 */
	@Test
	public void withdrawNotPermittedWhenNewBalanceIsTooSmall() {
		Assertions.assertThat(rule.getSettings().getMinPermittedBalance()).isEqualTo(100.0);
		boolean permitted = rule.withdrawPermitted(withdrawalAllowed, 90.0, Optional.empty());
		Assertions.assertThat(permitted).isFalse();
	}

	/**
	 * Tests that a withdrawal is permitted when new balance is permitted and no
	 * withdrawal in last period of days.
	 */
	@Test
	public void withdrawPermittedWhenNewBalanceIsAllowed() {
		Assertions.assertThat(currentBalance - withdrawalAllowed)
				.isGreaterThanOrEqualTo(rule.getSettings().getMinPermittedBalance());
		boolean permitted = rule.withdrawPermitted(withdrawalAllowed, currentBalance, Optional.empty());
		Assertions.assertThat(permitted).isTrue();
	}

	/**
	 * Tests that a withdrawal is not permitted when allowed withdrawn amount per
	 * period is exceeded
	 */
	@Test
	public void withdrawNotPermittedWhenMaxAmountPerPeriodIsExceeded() {
		Optional<MoneyTransaction> latestWithdrwal = Optional.of(new MoneyTransaction(LocalDateTime.now(),
				OperationType.WITHDRAWAL, withdrawalAllowed, currentBalance, "ATM"));
		boolean permitted = rule.withdrawPermitted(withdrawalAllowed, currentBalance, latestWithdrwal);
		Assertions.assertThat(permitted).isFalse();
	}
	
	/**
	 * Tests that a withdrawal is permitted when all rules are respected
	 */
	@Test
	public void withdrawPermitted() {
		Optional<MoneyTransaction> latestWithdrwal = Optional.of(new MoneyTransaction(LocalDateTime.now(),
				OperationType.WITHDRAWAL, withdrawalAllowed/2, currentBalance, "ATM"));
		boolean permitted = rule.withdrawPermitted(withdrawalAllowed, currentBalance, latestWithdrwal);
		Assertions.assertThat(permitted).isTrue();
	}

}
