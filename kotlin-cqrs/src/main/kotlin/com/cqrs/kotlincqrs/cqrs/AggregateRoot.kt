package com.cqrs.kotlincqrs.cqrs

import java.util.*

interface Event

abstract class AggregateRoot(val id: UUID = UUID.randomUUID()) {

    var version: Int = 0
    private val changes: MutableList<Event> = mutableListOf()

    suspend fun getUncommittedChanges() = changes.toList()

    abstract suspend fun apply(event: Event)

    suspend fun updateChanges(event: Event) {
        changes.add(event)
    }

    private suspend fun applyChange(event: Event, isNew: Boolean) {
        apply(event)

        if (isNew) {
            changes.add(event)
        }
    }

    suspend fun replayEvents(events: List<Event>) {
        events.forEach { applyChange(it, false) }
    }

}