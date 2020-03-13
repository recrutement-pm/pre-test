/*
 * =============================================================================
 *
 *   PRICE MINISTER APPLICATION
 *   Copyright (c) 2000 Babelstore.
 *   All Rights Reserved.
 *
 *   $Source$
 *   $Revision$
 *   $Date$
 *   $Author$
 *
 * =============================================================================
 */
package com.priceminister.account.implementation;

import com.priceminister.account.abstraction.AccountRule;


public class CustomerAccountRule implements AccountRule {

    /* (non-Javadoc)
     * @see com.priceminister.account.abstraction.AccountRule#withdrawPermitted(java.lang.Double)
     */
    public boolean withdrawPermitted(Double resultingAccountBalance) {
        return resultingAccountBalance >= 0;
    }

}
