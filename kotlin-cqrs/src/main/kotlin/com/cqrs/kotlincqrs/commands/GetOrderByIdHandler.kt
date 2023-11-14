package com.cqrs.kotlincqrs.commands

import com.cqrs.kotlincqrs.cqrs.CommandHandler
import com.cqrs.kotlincqrs.domain.Order
import org.springframework.stereotype.Component

data class GetOrderById(val orderId: Long)

@Component
class GetOrderByIdHandler : CommandHandler<Order, GetOrderById> {

    override suspend fun supports(commandType: Class<*>): Boolean {
        return commandType == GetOrderById::class.java
    }

    override suspend fun handle(command: GetOrderById): Order {
        return Order("1", "2")
    }

}