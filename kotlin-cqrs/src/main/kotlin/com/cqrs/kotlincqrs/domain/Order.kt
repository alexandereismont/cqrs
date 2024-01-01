package com.cqrs.kotlincqrs.domain

data class Order(
    val orderId: String,
    val orderName: String,
    val orderStatus: String?,
    val quantity: Int
)


data class ProductTest(
    val productId: String,
    val productName: String,
)