package com.priceminister.account;

import java.util.Optional;

import com.priceminister.account.exception.IllegalBalanceException;
import com.priceminister.account.model.AccountSettings;
import com.priceminister.account.model.MoneyTransaction;

/**
 * Checks if the requested operation is permitted.
 */
public interface AccountRule {
    
    /**
     * Checks if the resulting account balance after a withdrawal is OK
     * for the specific type of account.
     * @param withdrawnAmount - the amount of the withdrawal
     * @param accountType - matching the account settings to compute the permitted withdrawal 
     * @return true if the operation is permitted, false otherwise
     * @throws IllegalBalanceException 
     */
    boolean withdrawPermitted(Double withdrawalAmount, Double currentBalance, Optional<MoneyTransaction> optional);
  
    /**
     * 
     * @return the applied settings when computing permissions
     */
    AccountSettings getSettings() ;
}
