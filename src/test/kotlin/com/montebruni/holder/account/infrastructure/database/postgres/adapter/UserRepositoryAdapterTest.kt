package com.montebruni.holder.account.infrastructure.database.postgres.adapter

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
            assertEquals(userModel.username, result?.username)
            assertEquals(userModel.password, result?.password)
            assertEquals(userModel.status.name, result?.status?.name)
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

    @Nested
    inner class FindByUsername {

        @Test
        fun `should find user by username`() {
            val userModel = createUserModel()

            every { repository.findByUsername(userModel.username) } returns userModel

            val result = adapter.findByUsername(userModel.username)

            assertNotNull(result)
            assertEquals(userModel.id, result?.id)
            assertEquals(userModel.username, result?.username)
            assertEquals(userModel.password, result?.password)
            assertEquals(userModel.status.name, result?.status?.name)
            assertEquals(userModel.createdAt, result?.createdAt)
        }

        @Test
        fun `should return null when not found user by username`() {
            val userModel = createUserModel()

            every { repository.findByUsername(userModel.username) } returns null

            val result = adapter.findByUsername(userModel.username)

            assertNull(result)
        }
    }
}
