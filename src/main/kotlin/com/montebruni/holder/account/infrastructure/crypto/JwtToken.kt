package com.montebruni.holder.account.infrastructure.crypto

import com.montebruni.holder.account.domain.crypto.TokenProvider
import com.montebruni.holder.account.domain.crypto.data.Token
import com.montebruni.holder.account.domain.crypto.data.TokenConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.stream.Collectors

@Component
class JwtToken(
    private val jwtEncoder: JwtEncoder,
    @Value("\${security.jwt.issuer}") private val issuer: String,
    @Value("\${security.jwt.expiration-seconds}") private val expiration: Long
) : TokenProvider {

    override fun encode(tokenConfiguration: TokenConfiguration): Token {
        val now = Instant.now()

        val roles = tokenConfiguration.roles.stream().collect(Collectors.joining(" "))

        val claims = JwtClaimsSet.builder()
            .issuer(issuer)
            .subject(tokenConfiguration.subject)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiration))
            .claim("roles", roles)
            .build()

        val tokenEncoded = jwtEncoder.encode(JwtEncoderParameters.from(claims))

        return Token(
            value = tokenEncoded.tokenValue,
            expiresAt = tokenEncoded.expiresAt?.epochSecond
                ?: throw IllegalArgumentException("Expiration time not found")
        )
    }
}
