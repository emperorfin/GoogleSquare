package com.adyen.android.assignment

import org.junit.Assert.assertEquals
import org.junit.Test

class CashRegisterTest {
    @Test(expected = IllegalArgumentException::class)
    fun testInvalidTransaction() {
        val cashRegister = CashRegister(Change.max())
        val price: Long = 6_99
        val amountPaid: Long = 5_00
        cashRegister.performTransaction(price, amountPaid)
    }

    @Test
    fun testBasic1() {
        val price: Long = 7
        val amountPaid: Long = 15
        val expected = Change()
            .put(Coin.FIVE_CENT, 1)
            .put(Coin.TWO_CENT, 1)
            .put(Coin.ONE_CENT, 1)
        test(price, amountPaid, expected)
    }

    @Test
    fun testBasic2() {
        val price: Long = 99
        val amountPaid: Long = 2_00
        val expected = Change()
            .put(Coin.ONE_EURO, 1)
            .put(Coin.ONE_CENT, 1)
        test(price, amountPaid, expected)
    }

    @Test
    fun testBasic3() {
        val price: Long = 13_37
        val amountPaid: Long = 20_00
        val expected = Change()
            .put(Bill.FIVE_EURO, 1)
            .put(Coin.ONE_EURO, 1)
            .put(Coin.FIFTY_CENT, 1)
            .put(Coin.TEN_CENT, 1)
            .put(Coin.TWO_CENT, 1)
            .put(Coin.ONE_CENT, 1)
        test(price, amountPaid, expected)
    }

    @Test
    fun testBasic4() {
        val price: Long = 3_528_00
        val amountPaid: Long = 4_000_00
        val expected = Change()
            .put(Bill.TWOHUNDRED_EURO, 2)
            .put(Bill.FIFTY_EURO, 1)
            .put(Bill.TWENTY_EURO, 1)
            .put(Coin.TWO_EURO, 1)
        test(price, amountPaid, expected)
    }

    private fun test(
        price: Long,
        amountPaid: Long,
        expectedChange: Change,
        cashRegister: CashRegister = CashRegister(Change.max())
    ) {
        val actualChange = cashRegister.performTransaction(price, amountPaid)
        val expectedChangeTotal = amountPaid - price
        assertEquals(expectedChangeTotal, actualChange.total)
        assertEquals("Invalid change for $expectedChangeTotal", expectedChange, actualChange)
    }
}
