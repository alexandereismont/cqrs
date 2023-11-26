package com.cqrs.kotlincqrs.events

import com.cqrs.kotlincqrs.cqrs.AggregateRoot
import com.cqrs.kotlincqrs.cqrs.Event
import com.cqrs.kotlincqrs.eventstore.EventRecord
import com.cqrs.kotlincqrs.eventstore.EventRecordRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class EventRecordService(
    private val eventRecordRepository: EventRecordRepository
) {

    suspend fun save(id: UUID, version: Int, aggregateType: String, eventType: String, payload: String) {
        val record = EventRecord(
            id = id,
            version = version,
            aggregateType = aggregateType,
            eventType = eventType,
            payload = payload
        )
        eventRecordRepository.save(record)
    }

    suspend fun <C : AggregateRoot> getAggregate(id: UUID, aggregateClazz: Class<C>): C {
        val aggregate = aggregateClazz.getDeclaredConstructor().newInstance()
        val events = eventRecordRepository.getEvents(id)
            .toList()
            .map {
                val clazz = Class.forName("com.cqrs.kotlincqrs.events.${it.eventType}")
                return@map Gson().fromJson(it.payload, clazz) as Event
            }

        aggregate.replayEvents(events)
        return aggregate
    }

}