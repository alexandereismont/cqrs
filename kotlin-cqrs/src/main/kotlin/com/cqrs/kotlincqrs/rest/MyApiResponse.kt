package com.cqrs.kotlincqrs.rest

import com.cqrs.kotlincqrs.domain.Order
import com.cqrs.kotlincqrs.domain.ProductTest
import io.swagger.v3.oas.annotations.media.Schema

sealed class MyApiResponse<out T: Any>

data class ResponseSuccess<out T: Any>(
    val status: String,
    @Schema(oneOf = [Order::class, ProductTest::class])
    val data: T
) : MyApiResponse<T>()

data class ResponseError(
    val status: String,
    val message: String? = null,
    val code: Int
) : MyApiResponse<Nothing>()