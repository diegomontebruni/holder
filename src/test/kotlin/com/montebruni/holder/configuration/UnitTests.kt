package com.montebruni.holder.configuration

import io.mockk.clearAllMocks
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import java.util.TimeZone

@ExtendWith(MockKExtension::class)
open class UnitTests {

    @BeforeEach
    fun clearMocks() {
        clearAllMocks()
    }

    @BeforeEach
    fun setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }
}
