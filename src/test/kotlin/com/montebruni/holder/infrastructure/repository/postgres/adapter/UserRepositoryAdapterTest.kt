package com.montebruni.holder.infrastructure.repository.postgres.adapter

import com.montebruni.holder.account.infrastructure.database.postgres.adapter.UserRepositoryAdapter
import com.montebruni.holder.account.infrastructure.database.postgres.repository.UserPostgresRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createUserModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class UserRepositoryAdapterTest(
    @MockK private val repository: UserPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: UserRepositoryAdapter

    @Nested
    inner class FindById {

        @Test
        fun `should find user by id`() {
            val userModel = createUserModel()

            every { repository.findByIdOrNull(userModel.id) } returns userModel

            val result = adapter.findById(userModel.id)

            assertNotNull(result)
            assertEquals(userModel.id, result?.id)
            assertEquals(userModel.name, result?.name)
            assertEquals(userModel.createdAt, result?.createdAt)
        }

        @Test
        fun `should return null when not found user by id`() {
            val userModel = createUserModel()

            every { repository.findByIdOrNull(userModel.id) } returns null

            val result = adapter.findById(userModel.id)

            assertNull(result)
        }
    }
}
