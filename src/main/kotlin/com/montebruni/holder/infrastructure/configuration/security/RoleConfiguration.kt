package com.montebruni.holder.infrastructure.configuration.security

import org.springframework.http.HttpMethod

data class RoleConfiguration(
    val authorities: List<String> = emptyList(),
    val url: String,
    val method: HttpMethod
)
