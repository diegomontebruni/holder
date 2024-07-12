package com.montebruni.holder.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.montebruni.holder.infrastructure.configuration.JacksonConfiguration
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc

@Import(value = [JacksonConfiguration::class, DisableApplicationSecurityFilter::class])
@ExtendWith(SpringExtension::class)
@Suppress("SpringJavaInjectionPointsAutowiringInspection")
open class BaseRestIT {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper
}
