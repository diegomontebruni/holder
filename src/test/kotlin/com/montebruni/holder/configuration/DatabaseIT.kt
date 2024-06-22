package com.montebruni.holder.configuration

import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer

@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
open class DatabaseIT(
    private val repositories: List<JpaRepository<*, *>> = emptyList()
) {

    constructor(repository: JpaRepository<*, *>) : this(listOf(repository))

    @BeforeEach
    fun cleanDb() = repositories.forEach { it.deleteAllInBatch() }

    companion object {

        @JvmStatic
        val postgresContainer = PostgreSQLContainer("postgres:14.7-alpine").apply {
            withUsername("app_holder")
            withPassword("app_holder")
            withDatabaseName("holder")
        }.also { it.start() }

        @JvmStatic
        @DynamicPropertySource
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)
        }
    }
}
