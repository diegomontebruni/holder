package com.montebruni.holder.account.presentation.rest

import com.montebruni.holder.account.application.usecase.CreateUser
import com.montebruni.holder.account.application.usecase.input.CreateUserInput
import com.montebruni.holder.configuration.BaseRestIT
import com.montebruni.holder.fixtures.createCreateUserRequest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserController::class])
class UserControllerIT : BaseRestIT() {

    @MockkBean
    private lateinit var createUser: CreateUser

    private val baseUrl = "/v1/users"

    @Nested
    inner class CreateUserTestCases {

        @Test
        fun `should create a new user with valid request`() {
            val request = createCreateUserRequest()
            val useCaseInputSlot = slot<CreateUserInput>()

            every { createUser.execute(capture(useCaseInputSlot)) } returns mockk()

            mockMvc.perform(
                post(baseUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
            )
                .andExpect(status().is2xxSuccessful)
                .run {
                    assertEquals(request.username, useCaseInputSlot.captured.username.value)
                }

            verify { createUser.execute(useCaseInputSlot.captured) }
        }

        @Test
        fun `should return 400 when username is invalid`() {
            val request = createCreateUserRequest().copy(username = "invalid")

            mockMvc.perform(
                post(baseUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
            )
                .andExpect(status().is4xxClientError)

            verify(exactly = 0) { createUser.execute(any()) }
        }
    }
}
