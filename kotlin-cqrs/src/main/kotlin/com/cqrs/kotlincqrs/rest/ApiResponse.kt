package com.cqrs.kotlincqrs.rest

interface Response

data class ResponseSuccess(
    val status: String,
    val data: Any
) : Response

data class ResponseError(
    val status: String,
    val message: String? = null,
    val code: Int
) : Response