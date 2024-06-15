package com.montebruni.holder.account.application.extension

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant

class InstantExtensionTest {

    @Test
    fun `should format when given pattern then return formatted string`() {
        val instant = Instant.parse("2024-06-15T15:25:00Z")
        val pattern = "dd/MM/yyyy : HH:mm"

        val formatted = instant.format(pattern)

        assertEquals("15/06/2024 : 12:25", formatted)
    }

    @Test
    fun `should getDate when given instant then return formatted date`() {
        val instant = Instant.parse("2024-06-15T15:25:00Z")

        val date = instant.getFormattedDate()

        assertEquals("15/06/2024", date)
    }

    @Test
    fun `should getTime when given instant then return formatted time`() {
        val instant = Instant.parse("2024-06-15T15:25:00Z")

        val time = instant.getFormattedTime()

        assertEquals("12:25", time)
    }
}
