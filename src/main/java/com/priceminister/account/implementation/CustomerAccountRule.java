/*
 * =============================================================================
 *
 *   PRICE MINISTER APPLICATION
 *   Copyright (c) 2000 Babelstore.
 *   All Rights Reserved.
 *
 *   $Source$
 *   $Revision$
 *   $Date$
 *   $Author$
 *
 * =============================================================================
 */
package com.priceminister.account.implementation;

import java.time.LocalDateTime;
import java.util.Optional;

import com.priceminister.account.AccountRule;
import com.priceminister.account.model.AccountSettings;
import com.priceminister.account.model.MoneyTransaction;

public class CustomerAccountRule implements AccountRule {
	private final AccountSettings settings;

	public CustomerAccountRule(AccountSettings settings) {
		this.settings = settings;
	}

	public AccountSettings getSettings() {
		return settings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.priceminister.account.AccountRule#withdrawPermitted(java.lang.Double)
	 */
	public boolean withdrawPermitted(Double withdrawnAmount, Double currentBalance,
			Optional<MoneyTransaction> lastWithdrawalOperation) {

		if (amountIsInvalid(withdrawnAmount, currentBalance)) {
			return false;
		}

		if (lastWithdrawalOperation.isPresent() && !firstWithdrawalInPeriod(lastWithdrawalOperation.get())) {
			if (!amountIsPermittedPerPeriod(withdrawnAmount, lastWithdrawalOperation.get())) {
				return false;
			}
		}

		return true;
	}

	private boolean firstWithdrawalInPeriod(final MoneyTransaction lastWithdrawalOperation) {
		return LocalDateTime.now().minusDays(settings.getPeriodInDays())
				.isAfter(lastWithdrawalOperation.getCreationDate());
	}

	private boolean amountIsInvalid(final Double withdrawnAmount, final Double currentBalance) {
		return (currentBalance - withdrawnAmount < settings.getMinPermittedBalance()
				|| withdrawnAmount > settings.getMaxWithdrawnAmountPerPeriod());
	}

	private boolean amountIsPermittedPerPeriod(final Double withdrawnAmount,
			final MoneyTransaction lastWithdrawalOperation) {
		return lastWithdrawalOperation.getAmount() + withdrawnAmount <= settings.getMaxWithdrawnAmountPerPeriod();
	}

}
