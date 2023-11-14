package com.cqrs.kotlincqrs.commands

import com.cqrs.kotlincqrs.cqrs.CommandHandler
import com.cqrs.kotlincqrs.domain.OrderAggregate
import com.cqrs.kotlincqrs.events.OrderCreatedEvent
import com.cqrs.kotlincqrs.eventstore.EventRecord
import com.cqrs.kotlincqrs.eventstore.EventRecordRepository
import com.google.gson.Gson
import org.springframework.stereotype.Component

data class CreateOrderResponse(
    val orderId: String
)

@Component
class CreateOrderHandler(
    private val eventRecordRepository: EventRecordRepository
) : CommandHandler<CreateOrderResponse, CreateOrderCommand> {
    override suspend fun supports(commandType: Class<*>): Boolean {
        return commandType == CreateOrderCommand::class.java
    }

    override suspend fun handle(command: CreateOrderCommand): CreateOrderResponse {
        val order = OrderAggregate(
            orderId = command.orderId,
            orderName = command.orderName
        )

        val record = EventRecord(
            id = order.id,
            version = 1,
            aggregateType = "OrderAggregate",
            eventType = "OrderCreatedEvent",
            payload = Gson().toJson(OrderCreatedEvent(
                orderId = command.orderId,
                orderName = command.orderName
                )
            )
        )

        eventRecordRepository.save(record)
        return CreateOrderResponse(order.id.toString())
    }
}