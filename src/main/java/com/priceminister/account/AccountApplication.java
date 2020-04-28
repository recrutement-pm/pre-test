package com.priceminister.account;

import java.io.Console;

import com.priceminister.account.exception.IllegalBalanceException;
import com.priceminister.account.exception.InvalidSettingsException;
import com.priceminister.account.implementation.CustomerAccount;
import com.priceminister.account.implementation.CustomerAccountRule;
import com.priceminister.account.model.AccountSettings;
import com.priceminister.account.model.AccountSettings.AccountSettingsBuilder;
import com.priceminister.account.model.AccountType;

public class AccountApplication {

	public static void main(String[] args) {

		Console console = System.console();
		int keepAlive = 1;
		while (keepAlive == 1) {
			try {
				CustomerAccount customerAccount = new CustomerAccount();
				final AccountSettings settings = new AccountSettingsBuilder().withAccountType(AccountType.BCA)
						.withMinPermittedBalance(10.0)
						.withPeriodInDays(7)
						.withMaxWithdrawelAmountPerPeriod(300.0)
						.build();
				CustomerAccountRule rule = new CustomerAccountRule(settings);
				System.out.println("New account created with following settings : " + rule.getSettings().toString());

				int moreOperation = 1;
				while (moreOperation == 1) {

					String deposit = console.readLine("add money: ");
					customerAccount.add(Double.valueOf(deposit));
					System.out.println("Your new balance is : " + customerAccount.getBalance());

					String withdraw = console.readLine("withdraw money: ");
					customerAccount.withdrawAndReportBalance(Double.valueOf(withdraw), rule);
					System.out.println("Your new balance is :" + customerAccount.getBalance());

					String moreOps = console.readLine("More operation? (yes/no) ");
					if (moreOps.equals("no")) {
						customerAccount.getOperations()
								.forEach(op -> System.out.println("Operation: " + op.toString()));
						moreOperation = 0;
					}
				}

			} catch (IllegalArgumentException e) {
				System.out.println("Something went wrong with your inputs");
			} catch (IllegalBalanceException e) {
				System.out.println("The operation is not permitted!");
			} catch (InvalidSettingsException e) {
				System.out.println("An unexpected error occured");
			}
			String quitApp = console.readLine("Quit? (yes/no) : ");
			if (quitApp.equals("yes")) {
				keepAlive = 0;
			}
		}
	}
}
