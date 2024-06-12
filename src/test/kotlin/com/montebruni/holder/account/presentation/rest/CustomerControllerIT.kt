package com.montebruni.holder.account.presentation.rest

import com.montebruni.holder.account.application.usecase.CompleteCustomerRegistration
import com.montebruni.holder.account.application.usecase.input.CompleteCustomerRegistrationInput
import com.montebruni.holder.configuration.BaseRestIT
import com.montebruni.holder.fixtures.createCompleteCustomerRegistrationRequest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [CustomerController::class])
class CustomerControllerIT : BaseRestIT() {

    @MockkBean
    private lateinit var completeCustomerRegistration: CompleteCustomerRegistration

    private val baseUrl = "/v1/customers"

    @Nested
    inner class CompleteCustomerRegistrationTestCases {

        @Test
        fun `should complete the registration of a customer with valid request`() {
            val request = createCompleteCustomerRegistrationRequest()
            val useCaseInputSlot = slot<CompleteCustomerRegistrationInput>()

            justRun { completeCustomerRegistration.execute(capture(useCaseInputSlot)) }

            mockMvc.perform(
                post("$baseUrl/complete-registration")
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
            )
                .andExpect(status().is2xxSuccessful)
                .run {
                    assertEquals(request.userId, useCaseInputSlot.captured.userId)
                    assertEquals(request.name, useCaseInputSlot.captured.name)
                }

            verify { completeCustomerRegistration.execute(useCaseInputSlot.captured) }
        }
    }
}
