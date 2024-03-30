package com.ecommerce.domain

interface Handler<T, R> {
    fun handle(): R
}