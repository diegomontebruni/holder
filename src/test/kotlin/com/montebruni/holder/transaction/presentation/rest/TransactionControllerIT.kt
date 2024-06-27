package com.montebruni.holder.transaction.presentation.rest

import com.montebruni.holder.configuration.BaseRestIT
import com.montebruni.holder.fixtures.createCreateTransactionRequest
import com.montebruni.holder.transaction.application.usecase.CreateTransaction
import com.montebruni.holder.transaction.application.usecase.input.CreateTransactionInput
import com.ninjasquad.springmockk.MockkBean
import io.mockk.justRun
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [TransactionController::class])
class TransactionControllerIT : BaseRestIT() {

    @MockkBean
    private lateinit var createTransaction: CreateTransaction

    private val baseUrl = "/v1/transactions"

    @Nested
    inner class CreateTransactionCases {

        @Test
        fun `should return 201 when transaction is created`() {
            val request = createCreateTransactionRequest()
            val useCaseInputSlot = slot<CreateTransactionInput>()

            justRun { createTransaction.execute(capture(useCaseInputSlot)) }

            mockMvc.perform(
                post(baseUrl)
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
            )
                .andExpect(status().isCreated)
                .run {
                    assertEquals(request.walletId, useCaseInputSlot.captured.walletId)
                    assertEquals(request.ticker, useCaseInputSlot.captured.ticker)
                    assertEquals(request.quantity, useCaseInputSlot.captured.quantity)
                    assertEquals(request.value, useCaseInputSlot.captured.value.value.toString())
                    assertEquals(request.operation, useCaseInputSlot.captured.operation)
                    assertEquals(request.type, useCaseInputSlot.captured.type)
                    assertEquals(request.description, useCaseInputSlot.captured.description)
                }
        }
    }
}
