package com.ecommerce.domain

interface Command<ID: AggregateId, R> {
    val aggregateId: ID
}