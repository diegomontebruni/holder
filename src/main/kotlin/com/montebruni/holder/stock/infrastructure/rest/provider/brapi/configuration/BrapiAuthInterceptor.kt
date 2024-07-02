package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.configuration

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value

class BrapiAuthInterceptor(
    @Value("\${app.providers.brapi.token}") private val authToken: String
) : RequestInterceptor {

    override fun apply(request: RequestTemplate) {
        request.header("Authorization", "Bearer $authToken")
    }
}
