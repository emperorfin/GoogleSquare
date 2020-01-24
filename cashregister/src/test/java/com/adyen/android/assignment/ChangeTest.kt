package com.adyen.android.assignment

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class ChangeTest {
    @Test
    fun testEquals() {
        val expected = Change()
            .put(Coin.FIVE_CENT, 3)
            .put(Coin.TWO_CENT, 1)
            .put(Bill.FIFTY_EURO, 2)
        val actual = Change()
            .put(Bill.FIFTY_EURO, 2)
            .put(Coin.FIVE_CENT, 3)
            .put(Coin.TWO_CENT, 1)
        assertEquals(expected, actual)
    }

    @Test
    fun testElementsDiffer() {
        val expected = Change()
            .put(Coin.TWO_EURO, 4)
            .put(Bill.TEN_EURO, 1)
            .put(Coin.FIFTY_CENT, 3)
            .put(Coin.TWENTY_CENT, 2)
        val actual = Change()
            .put(Coin.TWO_EURO, 4)
            .put(Coin.TEN_CENT, 1)
            .put(Coin.FIFTY_CENT, 3)
            .put(Coin.TWENTY_CENT, 2)
        assertNotEquals(expected, actual)
    }

    @Test
    fun testCountsDiffer() {
        val expected = Change()
            .put(Coin.TWO_EURO, 4)
            .put(Bill.ONEHUNDRED_EURO, 1)
            .put(Coin.FIFTY_CENT, 3)
            .put(Coin.TWENTY_CENT, 2)
        val actual = Change()
            .put(Coin.TWO_EURO, 3)
            .put(Coin.TWENTY_CENT, 1)
            .put(Coin.FIFTY_CENT, 2)
            .put(Bill.ONEHUNDRED_EURO, 1)
        assertNotEquals(expected, actual)
    }
}
