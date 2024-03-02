package application.client

import domain.Command
import domain.client.Client.ClientId

data class ClientCommand (
    override val aggregateId: ClientId,
    val name: String,
    val cpf: String,
    val metaData: Map<String, String>
): Command<ClientId, Unit>