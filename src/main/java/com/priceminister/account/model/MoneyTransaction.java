package com.priceminister.account.model;

import java.time.LocalDateTime;

public class MoneyTransaction extends Operation {

	private final Double amount;
	private final Double newBalance;
	private final String originType;

	public MoneyTransaction(LocalDateTime creationDate, OperationType type, Double amount, Double newBalance,
			String sourceType) {
		super(creationDate, type);
		this.amount = amount;
		this.newBalance = newBalance;
		this.originType = sourceType;
	}

	public Double getAmount() {
		return amount;
	}

	public Double getNewBalance() {
		return newBalance;
	}

	public String getSourceType() {
		return originType;
	}

	@Override
	public String toString() {
		return "MoneyTransaction [creationDate=" + creationDate + ", type=" + type + ", amount=" + amount
				+ ", newBalance=" + newBalance + ", originType=" + originType + "]";
	}

}
