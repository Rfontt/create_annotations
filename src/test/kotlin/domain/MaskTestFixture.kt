package domain

import domain.client.Client
import domain.client.Client.Address

object MaskTestFixture {

    fun client(addressNumber: String? = null) = Client(
        name = "Marlin Stone",
        cpf = "00000000000",
        address = Address(
            street = "Jr.Street",
            number = addressNumber,
            postalCode = "62375000"
        )
    )

    fun clientMasked(
        addressNumber: String? = null
    ) = client(addressNumber).let {
        it.copy(
            cpf = "*".repeat(it.cpf.length),
            address = Address(
                street = "*".repeat(it.address.street.length),
                number = it.address.number?.let {
                    number -> "*".repeat(number.length)
                },
                postalCode = "*".repeat(it.address.postalCode.length)
            )
        )
    }
}