package com.cqrs.kotlincqrs.domain

import com.cqrs.kotlincqrs.cqrs.AggregateRoot
import com.cqrs.kotlincqrs.cqrs.Event
import com.cqrs.kotlincqrs.events.OrderCreatedEvent
import com.cqrs.kotlincqrs.events.OrderUpdatedEvent

class OrderAggregate() : AggregateRoot() {

    private var orderId: String? = null
    private var orderName: String? = null
    private var orderStatus: String? = null
    private var quantity: Int? = null

    constructor(orderId: String, orderName: String) : this() {
        this.orderId = orderId
        this.orderName = orderName
    }

    override suspend fun apply(event: Event) {
        when (event) {
            is OrderCreatedEvent -> applyOrderCreated(event)
            is OrderUpdatedEvent -> applyOrderUpdated(event)
            else -> throw IllegalArgumentException("Unknown event: $event")
        }
        updateChanges(event)
    }

    private fun applyOrderUpdated(event: OrderUpdatedEvent) {
        if(this.orderStatus == "SHIPPED") {
            throw IllegalStateException("Cannot update a shipped order")
        }
        this.quantity = event.quantity
    }

    private fun applyOrderCreated(event: OrderCreatedEvent) {
        this.orderId = event.orderId
        this.orderName = event.orderName
    }

}

