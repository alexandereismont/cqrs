package com.cqrs.kotlincqrs.cqrs

import org.springframework.stereotype.Component

@Component
class CommandHandlerProvider(private val commandHandlers: List<CommandHandler<*, *>>) {

    // Find the appropriate command handler for a given command type
    suspend fun <R, C> findHandler(command: C): CommandHandler<R, C>? {
        val commandType = command!!::class.java
        return commandHandlers
            .filter { it.supports(commandType) }
            .map { it as CommandHandler<R, C> }
            .firstOrNull()
    }

    // Execute a command by finding the appropriate handler and invoking it
    suspend fun <R, C> handle(command: C): R {
        val handler = findHandler<R, C>(command)
        if (handler != null) {
            return handler.handle(command)
        } else {
            throw IllegalArgumentException("No command handler found for ${command!!::class.java.simpleName}")
        }
    }
}