package com.montebruni.holder.wallet.infrastructure.event.listener

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createUserCreatedEvent
import com.montebruni.holder.wallet.application.usecase.CreateWallet
import com.montebruni.holder.wallet.application.usecase.input.CreateWalletInput
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateWalletListenerTest(
    @MockK private val createWallet: CreateWallet
) : UnitTests() {

    @InjectMockKs
    private lateinit var listener: CreateWalletListener

    @Test
    fun `should call use case successfully`() {
        val event = createUserCreatedEvent()
        val inputSlot = slot<CreateWalletInput>()

        every { createWallet.execute(capture(inputSlot)) } returns mockk()

        listener.listener(event)

        val inputCaptured = inputSlot.captured
        assertEquals(event.getData().id, inputCaptured.userId)
        assertEquals(event.getData().managerId, inputCaptured.managerId)

        verify { createWallet.execute(inputSlot.captured) }
    }
}
