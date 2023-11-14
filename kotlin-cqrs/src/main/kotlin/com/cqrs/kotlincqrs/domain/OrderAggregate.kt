package com.cqrs.kotlincqrs.domain

import com.cqrs.kotlincqrs.cqrs.AggregateRoot

class OrderAggregate() : AggregateRoot() {

    var orderId: String? = null
    var orderName: String? = null

    constructor(orderId: String, orderName: String) : this() {
        this.orderId = orderId
        this.orderName = orderName
    }

}

