package domain

interface Handler<T, R> {
    fun handle(): R
}