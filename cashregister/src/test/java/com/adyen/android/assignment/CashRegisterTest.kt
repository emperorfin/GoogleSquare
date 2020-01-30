package com.adyen.android.assignment

import org.junit.Assert.assertEquals
import org.junit.Test

class CashRegisterTest {
    @Test(expected = CashRegister.TransactionException::class)
    fun testInvalidTransaction() {
        val cashRegister = CashRegister(Change.max())
        val price: Long = 6_99
        val amountPaid: Long = 5_00
        cashRegister.performTransaction(price, amountPaid)
    }

    @Test
    fun testZeroTransaction() {
        testWithMaxChange(0, 0, Change.none())
    }

    @Test
    fun testBasic1() {
        val price: Long = 7
        val amountPaid: Long = 15
        val expected = Change()
            .add(Coin.FIVE_CENT, 1)
            .add(Coin.TWO_CENT, 1)
            .add(Coin.ONE_CENT, 1)
        testWithMaxChange(price, amountPaid, expected)
    }

    @Test
    fun testBasic2() {
        val price: Long = 99
        val amountPaid: Long = 2_00
        val expected = Change()
            .add(Coin.ONE_EURO, 1)
            .add(Coin.ONE_CENT, 1)
        testWithMaxChange(price, amountPaid, expected)
    }

    @Test
    fun testBasic3() {
        val price: Long = 13_37
        val amountPaid: Long = 20_00
        val expected = Change()
            .add(Bill.FIVE_EURO, 1)
            .add(Coin.ONE_EURO, 1)
            .add(Coin.FIFTY_CENT, 1)
            .add(Coin.TEN_CENT, 1)
            .add(Coin.TWO_CENT, 1)
            .add(Coin.ONE_CENT, 1)
        testWithMaxChange(price, amountPaid, expected)
    }

    @Test
    fun testBasic4() {
        val price: Long = 3_528_00
        val amountPaid: Long = 4_000_00
        val expected = Change()
            .add(Bill.TWOHUNDRED_EURO, 2)
            .add(Bill.FIFTY_EURO, 1)
            .add(Bill.TWENTY_EURO, 1)
            .add(Coin.TWO_EURO, 1)
        testWithMaxChange(price, amountPaid, expected)
    }

    private fun testWithMaxChange(price: Long, amountPaid: Long, expectedChange: Change) {
        val changeInCashRegister = Change.max()
        val cashRegister = CashRegister(changeInCashRegister)
        val actualChange = cashRegister.performTransaction(price, amountPaid)
        val expectedChangeTotal = amountPaid - price
        assertEquals("Total change", expectedChangeTotal, actualChange.total)
        assertEquals("Invalid change for $expectedChangeTotal", expectedChange, actualChange)
        val expectedChangeInCashRegister = Change.max().apply {
            expectedChange.getElements().forEach {
                remove(it, expectedChange.getCount(it))
            }
        }
        assertEquals("Change in cash register", expectedChangeInCashRegister, changeInCashRegister)
    }
}
