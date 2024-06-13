package com.montebruni.holder.account.presentation.rest.request

import com.montebruni.holder.fixtures.createChangeUserPasswordRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class ChangeUserPasswordRequestTest {

    @Nested
    inner class ToInputCases {
        @Test
        fun `should convert request to input with valid data`() {
            val request = createChangeUserPasswordRequest()
            val input = request.toInput()

            assertEquals(request.username, input.username.value)
            assertEquals(request.oldPassword, input.oldPassword.value)
            assertEquals(request.newPassword, input.newPassword.value)
        }

        @Test
        fun `should throw error when has a invalid username`() {
            val request = createChangeUserPasswordRequest().copy(username = "invalidUsername")

            assertThrows<IllegalArgumentException> { request.toInput() }
        }

        @Test
        fun `should throw error when has a invalid password old password`() {
            val request = createChangeUserPasswordRequest().copy(oldPassword = "123")

            assertThrows<IllegalArgumentException> { request.toInput() }
        }

        @Test
        fun `should throw error when has a invalid password new password`() {
            val request = createChangeUserPasswordRequest().copy(newPassword = "123")

            assertThrows<IllegalArgumentException> { request.toInput() }
        }
    }
}
