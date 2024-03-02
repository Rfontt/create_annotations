package application.client

import domain.Handler
import domain.client.Client
import domain.client.ClientRepository
import java.util.*

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
                        street = metaData.get("street") ?: "",
                        number = metaData.get("number"),
                        postalCode = metaData.get("postalCode") ?: ""
                    )
                )
            )
        }
    }
}