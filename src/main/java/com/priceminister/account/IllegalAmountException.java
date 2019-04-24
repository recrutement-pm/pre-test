package com.priceminister.account;


public class IllegalAmountException extends Exception {

    private static final long serialVersionUID = -9204191749972551939L;

	private Double amount;

    public IllegalAmountException(Double illegalAmount) {
        amount = illegalAmount;
    }
    
    public String toString() {
        return "Illegal amount: " + amount;
    }
}
