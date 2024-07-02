package com.montebruni.holder.stock.infrastructure.rest.provider.brapi

import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.configuration.BrapiAuthInterceptor
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.configuration.BrapiHttpClientConfiguration
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.response.FindByTickerResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    url = "\${app.providers.brapi.host}",
    path = "/api",
    name = "brapi-client",
    configuration = [BrapiHttpClientConfiguration::class, BrapiAuthInterceptor::class]
)
interface BrapiHttpClient {

    @GetMapping("/quote/{ticker}")
    fun findByTicker(@PathVariable ticker: String): FindByTickerResponse
}
