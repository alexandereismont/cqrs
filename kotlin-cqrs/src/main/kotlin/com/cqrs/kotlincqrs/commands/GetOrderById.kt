package com.cqrs.kotlincqrs.commands

import java.util.*

data class GetOrderById(val orderId: UUID) : Command