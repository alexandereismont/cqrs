package com.cqrs.kotlincqrs.cqrs

import java.util.UUID

interface Event

abstract class AggregateRoot {

    val id: UUID = UUID.randomUUID()

    private val changes: MutableList<Event> = mutableListOf()

    suspend fun getUncommittedChanges() = changes.toList()

}