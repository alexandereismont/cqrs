package com.cqrs.kotlincqrs.commands

import com.cqrs.kotlincqrs.cqrs.CommandHandler
import com.cqrs.kotlincqrs.domain.Order
import com.cqrs.kotlincqrs.domain.OrderAggregate
import com.cqrs.kotlincqrs.events.EventRecordService
import com.cqrs.kotlincqrs.events.OrderCreatedEvent
import com.cqrs.kotlincqrs.events.OrderUpdatedEvent
import com.google.gson.Gson
import org.springframework.stereotype.Component

@Component
class OrderCommandHandler(
    private val eventRecordService: EventRecordService
) : CommandHandler<Order, Command> {

    val commands = listOf(
        CreateOrderCommand::class.java,
        UpdateOrderQuantityCommand::class.java,
        GetOrderById::class.java
    )

    override suspend fun supports(commandType: Class<*>): Boolean {
        return commands.contains(commandType)
    }

    override suspend fun handle(command: Command) : Order = when (command) {
        is CreateOrderCommand -> handleCreate(command)
        is UpdateOrderQuantityCommand -> handleUpdate(command)
        is GetOrderById -> getOrderById(command)
        else -> throw IllegalArgumentException("Unknown command: $command")
    }

    suspend fun handleCreate(command: CreateOrderCommand): Order {
        val order = OrderAggregate()

        val event = OrderCreatedEvent(orderId = command.orderId, orderName = command.orderName)
        order.apply(event)

        eventRecordService.save(
            id = order.id,
            version = 1,
            aggregateType = "OrderAggregate",
            eventType = "OrderCreatedEvent",
            payload = Gson().toJson(event)
        )

        return order.toOrder()
    }

    suspend fun handleUpdate(command: UpdateOrderQuantityCommand): Order {
        val order: OrderAggregate = eventRecordService.getAggregate(command.id, OrderAggregate::class.java)

        val event = OrderUpdatedEvent(
            quantity = command.quantity
        )
        order.apply(event)

        eventRecordService.save(
            id = order.id,
            version = order.version + 1,
            aggregateType = "OrderAggregate",
            eventType = "OrderUpdatedEvent",
            payload = Gson().toJson(event)
        )

        return order.toOrder()
    }

    suspend fun getOrderById(command: GetOrderById): Order {
        return eventRecordService.getAggregate(command.orderId, OrderAggregate::class.java)
            .toOrder()
    }
}