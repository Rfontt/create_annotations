package domain

import domain.client.Client
import io.kotest.core.spec.style.DescribeSpec

class MaskTest: DescribeSpec({
    describe("Mask Annotation Test") {
        it ("Should mask the client's postal code") {
            val client = Client(
                name = "Marlin Stone",
                cpf = "senectus",
                address = Client.Address(
                    street = "aperiri",
                    number = "ac",
                    postalCode = "accommodare"
                )
            )

            maskValue(client)

            println("client $client")
        }
    }
})