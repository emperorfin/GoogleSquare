package com.adyen.android.assignment

/**
 * The CashRegister class holds the logic for performing transactions.
 *
 * @param change The change that the CashRegister is holding.
 */
class CashRegister(private val change: Change) {
    /**
     * Performs a transaction for an item with a certain price and a given amount.
     *
     * @param price The price of the product.
     * @param amountPaid The amount paid by the shopper.
     *
     * @return The minimal amount of change.
     *
     * @throws IllegalArgumentException If amountGiven < price.
     */
    fun performTransaction(price: Long, amountPaid: Long): Change {
        // TODO: Implement logic.
        return Change.none()
    }
}
