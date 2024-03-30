package com.ecommerce.domain

import com.ecommerce.domain.MaskTestFixture.client
import com.ecommerce.domain.MaskTestFixture.clientMasked
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class MaskTest: DescribeSpec({
    describe("Mask Annotation Test") {
        it ("Should mask the client's address") {
            val addressNumber = "123"
            val client = client(addressNumber)

            maskValue(client)

            client shouldBe clientMasked(addressNumber)
        }

        it ("Should not mask a null value") {
            val client = client()

            maskValue(client)

            client.address.number shouldBe null
        }
    }
})