package com.cqrs.kotlincqrs.events

import com.cqrs.kotlincqrs.cqrs.Event

data class OrderUpdatedEvent(val quantity: Int) : Event
