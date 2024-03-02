package domain.client

import domain.AggregateId
import domain.Mask
import java.util.UUID

data class Client(
    val id: ClientId,
    val name: String,
    @property:Mask
    val cpf: String,
    @property:Mask
    val address: Address
) {
    data class Address(
        @property:Mask
        val street: String,
        @property:Mask
        val number: String?,
        @property:Mask
        val postalCode: String
    )

    data class ClientId(
        override val id: UUID
    ): AggregateId(id)
}