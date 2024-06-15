package com.montebruni.holder.account.application.extension

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant

class InstantExtensionTest {

    private val dateTime = Instant.parse("2024-06-15T15:25:00Z")

    @Test
    fun `should format when given pattern then return formatted string`() {
        val formatted = dateTime.formatFromPattern("dd/MM/yyyy : HH:mm")

        assertEquals("15/06/2024 : 12:25", formatted)
    }

    @Test
    fun `should getFormattedDate when given instant then return formatted date`() {
        assertEquals("15/06/2024", dateTime.getFormattedDate())
    }

    @Test
    fun `should getFormattedTime when given instant then return formatted time`() {
        assertEquals("12:25", dateTime.getFormattedTime())
    }
}
