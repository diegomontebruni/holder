package com.montebruni.holder.account.presentation.rest

import com.montebruni.holder.account.application.usecase.InitiatePasswordRecovery
import com.montebruni.holder.account.application.usecase.input.InitiatePasswordRecoveryInput
import com.montebruni.holder.configuration.BaseRestIT
import com.montebruni.holder.fixtures.createInitiatePasswordRecoverRequest
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

@WebMvcTest(controllers = [AuthenticationController::class])
class AuthenticationControllerIT : BaseRestIT() {

    @MockkBean
    private lateinit var initiatePasswordRecovery: InitiatePasswordRecovery

    private val baseUrl = "/v1/auth"

    @Nested
    inner class InitiatePasswordRecoveryCases {

        @Test
        fun `should return 204 when password recovery is initiated`() {
            val request = createInitiatePasswordRecoverRequest()
            val useCaseInput = slot<InitiatePasswordRecoveryInput>()

            justRun { initiatePasswordRecovery.execute(capture(useCaseInput)) }

            mockMvc.perform(
                post("$baseUrl/initiate-password-recovery")
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
            )
                .andExpect(status().isNoContent)
                .run {
                    assertEquals(request.username, useCaseInput.captured.username.value)
                }

            verify { initiatePasswordRecovery.execute(useCaseInput.captured) }
        }
    }
}
