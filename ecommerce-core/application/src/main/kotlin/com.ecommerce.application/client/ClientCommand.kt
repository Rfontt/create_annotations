package com.ecommerce.application.client

import com.ecommerce.domain.Command
import com.ecommerce.domain.client.Client.ClientId

data class ClientCommand (
    override val aggregateId: ClientId,
    val name: String,
    val cpf: String,
    val metaData: Map<String, String>
): Command<ClientId, Unit>