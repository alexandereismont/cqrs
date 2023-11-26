package com.cqrs.kotlincqrs.eventstore

import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface EventRecordRepository : CoroutineCrudRepository<EventRecord, Long> {

    @Query("SELECT * FROM event_store WHERE id = :id ORDER BY version")
    fun getEvents(id: UUID) : Flow<EventRecord>

}