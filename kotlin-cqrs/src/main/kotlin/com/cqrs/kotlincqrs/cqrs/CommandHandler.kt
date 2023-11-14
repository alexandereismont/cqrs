package com.cqrs.kotlincqrs.cqrs

interface CommandHandler<R, C> {
   suspend fun supports(commandType: Class<*>): Boolean
   suspend fun handle(command: C): R
}