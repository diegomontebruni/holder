package com.montebruni.holder.infrastructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.stereotype.Component

@Component
class DisableApplicationSecurityFilter {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain = httpSecurity
        .csrf { it.disable() }
        .authorizeHttpRequests { it.anyRequest().permitAll() }
        .build()
}
