package com.cqrs.kotlincqrs.commands

data class CreateOrderCommand(val orderId: String, val orderName: String) : Command