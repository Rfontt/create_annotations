package domain.client

import domain.Mask

data class Client(
    @property:Mask
    val name: String,
    val cpf: String,
    @property:Mask
    val address: Address
) {
    data class Address(
        val street: String,
        val number: String,
        @property:Mask
        val postalCode: String
    )
}