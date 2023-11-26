package com.cqrs.kotlincqrs.cqrs

import com.cqrs.kotlincqrs.commands.Command

interface CommandHandler<R, C> {
   suspend fun supports(commandType: Class<*>): Boolean
   suspend fun handle(command: Command): R
}