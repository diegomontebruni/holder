package com.montebruni.holder.infrastructure.configuration.security

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import kotlin.let

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Suppress("SpreadOperator")
class SecurityConfiguration(
    private val requestRole: List<RequestRole>
) {

    @Value("\${security.jwt.key.public}")
    private lateinit var publicKey: RSAPublicKey

    @Value("\${security.jwt.key.private}")
    private lateinit var privateKey: RSAPrivateKey

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("")
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles")

        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter)
        return jwtAuthenticationConverter
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity) =
        http
            .authorizeHttpRequests { authorize ->
                requestRole.forEach { role ->
                    role.roleConfiguration.forEach { roleConfiguration ->
                        authorize
                            .requestMatchers(roleConfiguration.method, roleConfiguration.url)
                            .takeIf { roleConfiguration.authorities.isNotEmpty() }
                            ?.hasAnyAuthority(*roleConfiguration.authorities.toTypedArray())
                            ?: authorize.requestMatchers(roleConfiguration.method, roleConfiguration.url).permitAll()
                    }
                }

                authorize.anyRequest().authenticated()
            }
            .csrf { it.disable() }
            .oauth2ResourceServer { it.jwt(Customizer.withDefaults()) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()

    @Bean
    fun jwtEncoder(): JwtEncoder =
        RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .build()
            .let(::JWKSet)
            .let { ImmutableJWKSet<SecurityContext>(it) }
            .let(::NimbusJwtEncoder)

    @Bean
    fun jwtDecoder(): JwtDecoder = NimbusJwtDecoder.withPublicKey(publicKey).build()
}
