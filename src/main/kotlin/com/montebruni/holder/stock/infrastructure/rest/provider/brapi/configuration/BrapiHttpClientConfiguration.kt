package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.configuration

import feign.Retryer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class BrapiHttpClientConfiguration(
    @Value("\${app.providers.brapi.retry.initial-await-time-ms}") private val initialAwaitTimeMs: Long,
    @Value("\${app.providers.brapi.retry.max-await-time-ms}") private val maxAwaitTimeMs: Long,
    @Value("\${app.providers.brapi.retry.attempts}") private val attempts: Int
) {

    @Bean
    fun retryer() = Retryer.Default(initialAwaitTimeMs, maxAwaitTimeMs, attempts)
}
