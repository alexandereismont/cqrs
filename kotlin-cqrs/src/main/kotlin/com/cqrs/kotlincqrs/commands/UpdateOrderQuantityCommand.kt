package com.cqrs.kotlincqrs.commands

import java.util.UUID

data class UpdateOrderQuantityCommand(val id: UUID, val quantity: Int) : Command
