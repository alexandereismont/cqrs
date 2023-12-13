package com.cqrs.kotlincqrs.domain

import com.cqrs.kotlincqrs.cqrs.AggregateRoot
import com.cqrs.kotlincqrs.cqrs.Event
import com.cqrs.kotlincqrs.events.OrderCreatedEvent
import com.cqrs.kotlincqrs.events.OrderUpdatedEvent
import java.util.UUID

class OrderAggregate(id: UUID = UUID.randomUUID()) : AggregateRoot(id) {

    private var orderId: String? = null
    private var orderName: String? = null
    private var orderStatus: String? = null
    private var quantity: Int = 0

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

    fun toOrder(): Order {
        return Order(
            orderId = this.orderId!!,
            orderName = this.orderName!!,
            orderStatus = this.orderStatus,
            quantity = this.quantity
        )
    }

}

