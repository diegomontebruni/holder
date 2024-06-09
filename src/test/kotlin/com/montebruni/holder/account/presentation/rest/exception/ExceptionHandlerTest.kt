package com.montebruni.holder.account.presentation.rest.exception

import com.montebruni.holder.configuration.UnitTests
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ExceptionHandlerTest(
    @RelaxedMockK private val illegalArgumentException: IllegalArgumentException
) : UnitTests() {

    private val handler: ExceptionHandler = ExceptionHandler()

    @Nested
    inner class HandleIllegalArgumentException {

        @Test
        fun `should return 400 when illegal argument exception was handled`() {
            val response = handler.handleIllegalArgumentException(illegalArgumentException)

            assertTrue(response.statusCode.is4xxClientError)
        }
    }
}
