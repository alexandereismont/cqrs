package com.cqrs.kotlincqrs.repository

import com.cqrs.kotlincqrs.domain.Order
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface OrderRepository : CoroutineCrudRepository<Order, Long>