package com.cqrs.kotlincqrs.rest

import com.cqrs.kotlincqrs.cqrs.CommandHandlerProvider
import com.cqrs.kotlincqrs.commands.GetOrderById
import com.cqrs.kotlincqrs.domain.Order
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(
    private val commandHandlerProvider: CommandHandlerProvider
) {

    @GetMapping(path = ["{id}"])
    suspend fun getOrderById(@PathVariable id: Long): Order {
        return commandHandlerProvider.handle(GetOrderById(id))
    }

}