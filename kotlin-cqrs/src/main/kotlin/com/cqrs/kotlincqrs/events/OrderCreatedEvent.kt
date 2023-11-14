package com.cqrs.kotlincqrs.events

import com.cqrs.kotlincqrs.cqrs.Event

data class OrderCreatedEvent (
    val orderId: String,
    val orderName: String
) : Event