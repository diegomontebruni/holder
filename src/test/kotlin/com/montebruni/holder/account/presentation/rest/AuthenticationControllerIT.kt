package com.montebruni.holder.account.presentation.rest

import com.montebruni.holder.account.application.usecase.InitiatePasswordRecovery
import com.montebruni.holder.account.application.usecase.Login
import com.montebruni.holder.account.application.usecase.RecoverPassword
import com.montebruni.holder.account.application.usecase.input.InitiatePasswordRecoveryInput
import com.montebruni.holder.account.application.usecase.input.LoginInput
import com.montebruni.holder.account.application.usecase.input.RecoverPasswordInput
import com.montebruni.holder.configuration.BaseRestIT
import com.montebruni.holder.fixtures.createInitiatePasswordRecoverRequest
import com.montebruni.holder.fixtures.createLoginOutput
import com.montebruni.holder.fixtures.createLoginRequest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [AuthenticationController::class])
class AuthenticationControllerIT : BaseRestIT() {

    @MockkBean
    private lateinit var initiatePasswordRecovery: InitiatePasswordRecovery

    @MockkBean
    private lateinit var recoverPassword: RecoverPassword

    @MockkBean
    private lateinit var login: Login

    private val baseUrl = "/v1/auth"

    @Nested
    inner class LoginCases {

        @Test
        fun `should return 200 when user is authenticated`() {
            val request = createLoginRequest()
            val useCaseInput = slot<LoginInput>()
            val output = createLoginOutput()

            every { login.execute(capture(useCaseInput)) } returns output

            mockMvc.perform(
                post("$baseUrl/login")
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
            )
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("accessToken").value(output.accessToken))
                .andExpect(MockMvcResultMatchers.jsonPath("expiresAt").value(output.expiresAt))

            assertEquals(request.username, useCaseInput.captured.username.value)
            assertEquals(request.password, useCaseInput.captured.password.value)

            verify { login.execute(useCaseInput.captured) }
        }
    }

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

    @Nested
    inner class RecoverPasswordCases {

        @Test
        fun `should return 204 when password is recovered`() {
            val token = "token"
            val useCaseInput = slot<RecoverPasswordInput>()

            justRun { recoverPassword.execute(capture(useCaseInput)) }

            mockMvc.perform(
                post("$baseUrl/password-recover")
                    .contentType(APPLICATION_JSON)
                    .param("token", token)
            )
                .andExpect(status().isNoContent)
                .run { assertEquals(token, useCaseInput.captured.token) }

            verify { recoverPassword.execute(useCaseInput.captured) }
        }
    }
}
