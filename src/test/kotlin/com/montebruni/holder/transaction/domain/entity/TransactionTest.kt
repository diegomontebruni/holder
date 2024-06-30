package com.montebruni.holder.transaction.domain.entity

import com.montebruni.holder.fixtures.createTransaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class TransactionTest {

    @Nested
    inner class ToConfirmedCases {

        @ParameterizedTest
        @EnumSource(Status::class, names = ["PENDING"], mode = EnumSource.Mode.EXCLUDE)
        fun `should return null when try to change status to confirmed`(status: Status) {
            val transaction = createTransaction().copy(status = status)

            assertNull(transaction.toConfirmed())
        }

        @Test
        fun `should return transaction with confirmed status`() {
            val transaction = createTransaction()

            val confirmedTransaction = transaction.toConfirmed()

            assertEquals(Status.CONFIRMED, confirmedTransaction?.status)
        }
    }

    @Nested
    inner class ToFailedCases {

        @ParameterizedTest
        @EnumSource(Status::class, names = ["PENDING"], mode = EnumSource.Mode.EXCLUDE)
        fun `should return null when try to change status to failed`(status: Status) {
            val transaction = createTransaction().copy(status = status)

            assertNull(transaction.toFailed())
        }

        @Test
        fun `should return transaction with failed status`() {
            val transaction = createTransaction()

            val failedTransaction = transaction.toFailed()

            assertEquals(Status.FAILED, failedTransaction?.status)
        }
    }
}
