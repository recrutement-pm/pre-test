package com.priceminister.account;


public class IllegalAmountException extends Exception {

	private Double amount;

    public IllegalAmountException(Double illegalAmount) {
        super("Illegal amount: " + illegalAmount);
        amount = illegalAmount;
    }
    
    public String toString() {
        return "Illegal amount: " + amount;
    }
}
