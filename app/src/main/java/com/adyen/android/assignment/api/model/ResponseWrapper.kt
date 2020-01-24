package com.adyen.android.assignment.api.model

data class ResponseWrapper<T>(
    val meta: Meta,
    val response: T
)
