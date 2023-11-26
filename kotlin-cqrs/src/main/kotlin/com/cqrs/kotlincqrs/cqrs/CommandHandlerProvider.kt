package com.cqrs.kotlincqrs.cqrs

import com.cqrs.kotlincqrs.commands.Command
import org.springframework.stereotype.Component

@Component
class CommandHandlerProvider(private val commandHandlers: List<CommandHandler<*, *>>) {

    suspend fun <R, C> findHandler(command: C): CommandHandler<R, C>? {
        val commandType = command!!::class.java
        return commandHandlers
            .filter { it.supports(commandType) }
            .map { it as CommandHandler<R, C> }
            .firstOrNull()
    }

    suspend fun <R> handle(command: Command): R {
        val handler = findHandler<R, Command>(command)
        if (handler != null) {
            return handler.handle(command)
        } else {
            throw IllegalArgumentException("No command handler found for ${command::class.java.simpleName}")
        }
    }
}