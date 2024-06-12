package com.montebruni.holder.account.presentation.rest.request

import com.montebruni.holder.fixtures.createCompleteCustomerRegistrationRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CompleteCustomerRegistrationRequestTest {

    @Test
    fun `should convert request to input`() {
        val request = createCompleteCustomerRegistrationRequest()
        val input = request.toCompleteCustomerRegistrationInput()

        assertEquals(request.userId, input.userId)
        assertEquals(request.name, input.name)
    }
}
