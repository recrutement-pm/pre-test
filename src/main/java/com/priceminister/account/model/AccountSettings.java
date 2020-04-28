package com.priceminister.account.model;

import com.priceminister.account.exception.InvalidSettingsException;

public class AccountSettings {
	final AccountType accountType;
	final Double minPermittedBalance;
	final Double maxPermittedBalance;
	final int periodInDays;
	final Double maxWithdrawnAmountPerPeriod;

	public AccountSettings(AccountType accountType, Double minPermittedBalance, Double maxPermittedBalance,
			int periodInDays, Double maxWithdrawelAmountPerPeriod) {
		this.accountType = accountType;
		this.minPermittedBalance = minPermittedBalance;
		this.maxPermittedBalance = maxPermittedBalance;
		this.periodInDays = periodInDays;
		this.maxWithdrawnAmountPerPeriod = maxWithdrawelAmountPerPeriod;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public Double getMinPermittedBalance() {
		return minPermittedBalance;
	}

	public Double getMaxPermittedBalance() {
		return maxPermittedBalance;
	}

	public int getPeriodInDays() {
		return periodInDays;
	}

	public Double getMaxWithdrawnAmountPerPeriod() {
		return maxWithdrawnAmountPerPeriod;
	}

	public static class AccountSettingsBuilder {
		private AccountType accountType;
		private Double minPermittedBalance = 0.0;
		private Double maxPermittedBalance = 0.0;
		private int periodInDays = 1;
		private Double maxWithdrawnAmountPerPeriod;

		public AccountSettingsBuilder withAccountType(AccountType accountType) {
			this.accountType = accountType;
			return this;
		}

		public AccountSettingsBuilder withMinPermittedBalance(Double minPermittedBalance) {
			this.minPermittedBalance = minPermittedBalance;
			return this;
		}

		public AccountSettingsBuilder withMaxPermittedBalance(Double maxPermittedBalance) {
			this.maxPermittedBalance = maxPermittedBalance;
			return this;
		}

		public AccountSettingsBuilder withPeriodInDays(int periodInDays) {
			this.periodInDays = periodInDays;
			return this;
		}

		public AccountSettingsBuilder withMaxWithdrawelAmountPerPeriod(Double maxWithdrawnAmountPerPeriod) {
			this.maxWithdrawnAmountPerPeriod = maxWithdrawnAmountPerPeriod;
			return this;
		}

		public AccountSettings build() throws InvalidSettingsException {
			if (maxWithdrawnAmountPerPeriod == null) {
				throw new InvalidSettingsException("maxWithdrawnAmountPerPeriod");
			}
			return new AccountSettings(accountType, minPermittedBalance, maxPermittedBalance, periodInDays,
					maxWithdrawnAmountPerPeriod);
		}
	}

	@Override
	public String toString() {
		return "Account type= " + accountType.getDescription() + ", minimum permitted balance= " + minPermittedBalance
				+ ", maximum permitted balance (applies only for saving accounts)= " + maxPermittedBalance
				+ ", maximum withdrawn amount " + maxWithdrawnAmountPerPeriod + " per " + periodInDays + " days";
	}

}
