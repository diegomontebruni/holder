package com.montebruni.holder.wallet.infrastructure.database

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createWallet
import com.montebruni.holder.fixtures.createWalletModel
import com.montebruni.holder.wallet.infrastructure.database.postgres.WalletPostgresRepository
import com.montebruni.holder.wallet.infrastructure.database.postgres.model.WalletPostgresModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID
import kotlin.collections.first
import kotlin.let

class WalletRepositoryAdapterTest(
    @MockK private val repository: WalletPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: WalletRepositoryAdapter

    @Nested
    inner class CreateWalletCases {

        @Test
        fun `should create wallet`() {
            val wallet = createWallet()
            val walletSlot = slot<WalletPostgresModel>()

            every { repository.save(capture(walletSlot)) } answers { walletSlot.captured }

            val result = adapter.create(wallet)

            assertEquals(walletSlot.captured.id, result.id)
            assertEquals(walletSlot.captured.managerId, result.managerId)
            assertEquals(walletSlot.captured.balance, result.balance.value.toDouble())
            assertEquals(walletSlot.captured.createdAt, result.createdAt)

            verify { repository.save(walletSlot.captured) }
        }
    }

    @Nested
    inner class FindByIdCases {

        @Test
        fun `should find wallet by id`() {
            val walletModel = createWalletModel()

            every { repository.findByIdOrNull(walletModel.id) } returns walletModel

            val result = adapter.findById(walletModel.id)

            assertEquals(walletModel.id, result?.id)
            assertEquals(walletModel.managerId, result?.managerId)
            assertEquals(walletModel.balance, result?.balance?.value?.toDouble())
            assertEquals(walletModel.createdAt, result?.createdAt)

            verify { repository.findByIdOrNull(walletModel.id) }
        }

        @Test
        fun `should return null when not found wallet by id`() {
            val walletModel = createWalletModel()

            every { repository.findByIdOrNull(walletModel.id) } returns null

            adapter.findById(walletModel.id).let(::assertNull)

            verify { repository.findByIdOrNull(walletModel.id) }
        }
    }

    @Nested
    inner class FindByManagerIdCases {

        @Test
        fun `should find wallets by manager id`() {
            val managerId = UUID.randomUUID()
            val walletsModel = listOf(createWalletModel())
            val mangerIdSlot = slot<UUID>()

            every { repository.findByManagerId(capture(mangerIdSlot)) } returns walletsModel

            val result = adapter.findByManagerId(managerId).first()

            val walletModel = walletsModel.first()
            assertEquals(managerId, mangerIdSlot.captured)
            assertEquals(walletModel.id, result.id)
            assertEquals(walletModel.managerId, result.managerId)
            assertEquals(walletModel.balance, result.balance.value.toDouble())
            assertEquals(walletModel.createdAt, result.createdAt)

            verify { repository.findByManagerId(mangerIdSlot.captured) }
        }

        @Test
        fun `should return empty list when not found wallet by manager id`() {
            val managerId = UUID.randomUUID()
            val mangerIdSlot = slot<UUID>()

            every { repository.findByManagerId(capture(mangerIdSlot)) } returns emptyList()

            adapter.findByManagerId(managerId).let { assertTrue(it.isEmpty()) }

            assertEquals(managerId, mangerIdSlot.captured)

            verify { repository.findByManagerId(mangerIdSlot.captured) }
        }
    }
}
