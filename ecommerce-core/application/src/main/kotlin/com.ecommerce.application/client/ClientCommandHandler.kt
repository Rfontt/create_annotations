package com.ecommerce.application.client

import com.ecommerce.domain.Handler
import com.ecommerce.domain.client.Client
import com.ecommerce.domain.client.ClientRepository

class ClientCommandHandler(
    private val clientRepository: ClientRepository,
    private val clientCommand: ClientCommand
): Handler<ClientCommand, Unit> {

    override fun handle() {

        with(clientCommand) {
            clientRepository.save(
                Client(
                    id = aggregateId,
                    name = name,
                    cpf = cpf,
                    address = Client.Address(
                        street = metaData["street"] ?: "",
                        number = metaData["number"],
                        postalCode = metaData["postalCode"] ?: ""
                    )
                )
            )
        }
    }
}