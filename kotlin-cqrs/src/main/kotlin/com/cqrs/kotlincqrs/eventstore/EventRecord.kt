package com.cqrs.kotlincqrs.eventstore

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("event_store")
data class EventRecord(
    val id : UUID,
    val version : Int,
    val aggregateType: String,
    val eventType : String,
    val payload : String,
    val timestamp : LocalDateTime = LocalDateTime.now()
)