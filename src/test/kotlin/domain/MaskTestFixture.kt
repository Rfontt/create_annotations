package domain

import domain.client.Client
import domain.client.Client.Address
import java.util.UUID

object MaskTestFixture {

    fun client(addressNumber: String? = null) = Client(
        id = Client.ClientId(
            id = UUID.fromString("6158173d-c761-4960-a241-b7b4716a63b7")
        ),
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