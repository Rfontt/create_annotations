package com.ecommerce.domain.client

interface ClientRepository {
    fun save(client: Client)
}