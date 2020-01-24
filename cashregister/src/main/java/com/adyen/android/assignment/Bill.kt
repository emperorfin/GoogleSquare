package com.adyen.android.assignment

enum class Bill(override val minorValue: Int) : MonetaryElement {
    FIVEHUNDRED_EURO(500_00),
    TWOHUNDRED_EURO(200_00),
    ONEHUNDRED_EURO(100_00),
    FIFTY_EURO(50_00),
    TWENTY_EURO(20_00),
    TEN_EURO(10_00),
    FIVE_EURO(5_00);
}
