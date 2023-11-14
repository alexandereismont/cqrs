package com.cqrs.kotlincqrs.eventstore

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface EventRecordRepository : CoroutineCrudRepository<EventRecord, Long>