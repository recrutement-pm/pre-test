package com.priceminister.account;

/**
 * Exceptions related to bad account operation config.
 */
public class AccountOperationConfigException extends Exception{

    public static final String NULL_WITHDRAWN_AMOUNT = "the withdrawn amount is null";
    public static final String NULL_ADDED_AMOUNT = "the added amount is null";
    public static final String THE_ACCOUNT_RULE_IS_NULL = "the account rule is null";
    public static final String NEGATIVE_ADDED_AMOUNT = "the added amount is negative";
    public static final String NEGATIVE_WITHDRAWN_AMOUNT = "the added amount is negative";


    private String errorCode;

    public AccountOperationConfigException(String s) {
        errorCode = s;
    }

    @Override
    public String toString() {
        return errorCode;
    }
}
