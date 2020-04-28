package com.priceminister.account.exception;

public class InvalidSettingsException extends Exception {

	private static final long serialVersionUID = 5868987164581127411L;

	final String settings;

	public InvalidSettingsException(String settings) {
		this.settings = settings;
	}

	public String toString() {
		return "Invalid account settings: " + settings;
	}
}
