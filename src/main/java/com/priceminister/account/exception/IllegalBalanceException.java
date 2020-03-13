package com.priceminister.account.exception;


/**
 * Exception managing the balance issues
 */
public class IllegalBalanceException extends Exception {
    
    private static final long serialVersionUID = -9204191749972551939L;
    
    public IllegalBalanceException(Double illegalBalance) {
        super("Illegal account balance: " + illegalBalance);
    }

    public IllegalBalanceException(String message) {
        super(message);
    }
    
    public String toString() {
        return getLocalizedMessage();
    }
}
