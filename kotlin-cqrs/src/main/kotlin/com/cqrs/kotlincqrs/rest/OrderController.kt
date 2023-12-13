package com.cqrs.kotlincqrs.rest

import com.cqrs.kotlincqrs.commands.CreateOrderCommand
import com.cqrs.kotlincqrs.commands.UpdateOrderQuantityCommand
import com.cqrs.kotlincqrs.cqrs.CommandHandlerProvider
import com.cqrs.kotlincqrs.commands.GetOrderById
import com.cqrs.kotlincqrs.domain.Order
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/orders")
class OrderController(
    private val commandHandlerProvider: CommandHandlerProvider
) {

    @GetMapping(path = ["{id}"])
    suspend fun getOrderById(@PathVariable id: UUID): Order {
        return commandHandlerProvider.handle(command = GetOrderById(id))
    }

    @PostMapping
    suspend fun createOrder(createOrder: CreateOrderCommand): Order {
        return commandHandlerProvider.handle(command = createOrder)
    }

    @PutMapping("/{id}")
    suspend fun updateQuantity(@PathVariable id: UUID, @RequestBody quantity: UpdateOrderQuantityDto) {
        val command = UpdateOrderQuantityCommand(id = id, quantity = quantity.quantity)
        return commandHandlerProvider.handle(command = command)
    }

}

data class UpdateOrderQuantityDto(val quantity: Int)