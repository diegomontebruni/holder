package com.montebruni.holder.account.domain.entity

import com.montebruni.holder.account.domain.exception.UserAlreadyRegisteredException
import com.montebruni.holder.fixtures.createUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class UserTest {

    @Nested
    inner class IsPendingCases {

        @Test
        fun `should return true when status is pending`() {
            assertTrue(createUser().isPending())
        }

        @ParameterizedTest
        @EnumSource(Status::class, names = ["PENDING"], mode = EnumSource.Mode.EXCLUDE)
        fun `should return false when status is different than pending`(status: Status) {
            assertFalse(createUser().copy(status = status).isPending())
        }
    }

    @Nested
    inner class canBeRegisteredCases {

        @Test
        fun `should return true when user can be registered`() {
            assertTrue(createUser().canBeRegistered())
        }

        @ParameterizedTest
        @EnumSource(Status::class, names = ["PENDING"], mode = EnumSource.Mode.EXCLUDE)
        fun `should throw exception when user can not be registered`(status: Status) {
            assertThrows<UserAlreadyRegisteredException> { createUser().copy(status = status).canBeRegistered() }
        }
    }

    @Nested
    inner class ActivateCases {

        @Test
        fun `should change status to active`() {
            val user = createUser().copy(status = Status.PENDING).activate()

            assertEquals(Status.ACTIVE, user.status)
        }
    }
}
