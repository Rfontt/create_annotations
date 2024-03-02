package domain.client

interface ClientRepository {
    fun save(client: Client): Client
}