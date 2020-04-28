package com.priceminister.account.model;

public enum AccountType {

	BCA("BASIC_CHECKING_ACCOUNT"), YCA("YOUNG_CHECKING_ACCOUNT"), SA("SAVING_ACCOUNT");

	private String description;

	public String getDescription() {
		return this.description;
	}

	private AccountType(String description) {
		this.description = description;
	}
}
