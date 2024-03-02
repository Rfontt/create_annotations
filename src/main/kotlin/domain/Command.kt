package domain

interface Command<ID: AggregateId, R> {
    val aggregateId: ID
}