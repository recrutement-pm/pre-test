package com.priceminister.account.model;

import java.time.LocalDateTime;

public class Operation {
	protected final LocalDateTime creationDate;
	protected final OperationType type;
	
	public Operation(LocalDateTime creationDate, OperationType type) {
		this.creationDate = creationDate;
		this.type = type;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public OperationType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Operation [creationDate= " + creationDate + ", type= " + type + "]";
	}
	
}
