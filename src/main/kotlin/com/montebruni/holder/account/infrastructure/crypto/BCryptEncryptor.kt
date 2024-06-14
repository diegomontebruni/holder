package com.montebruni.holder.account.infrastructure.crypto

import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.util.Base64
import java.util.UUID

@Component
@Suppress("MagicNumber")
class BCryptEncryptor : EncryptorProvider {

    private val passwordEncoder = BCryptPasswordEncoder()
    private val random = SecureRandom()

    override fun encrypt(value: String): String {
        val salt = ByteArray(16)
        random.nextBytes(salt)
        val saltString = Base64.getEncoder().encodeToString(salt)
        val encryptedValue = passwordEncoder.encode(rawValue(value, saltString))
        return "$saltString:$encryptedValue"
    }

    override fun validate(value: String, encryptedValue: String): Boolean =
        encryptedValue
            .split(":")
            .let { splitValue ->
                when (splitValue.size) {
                    1 -> passwordEncoder.matches(value, splitValue.first())
                    2 -> passwordEncoder.matches(rawValue(value, splitValue.first()), splitValue.last())
                    else -> false
                }
            }

    override fun randomToken(): String =
        UUID
            .randomUUID()
            .toString()
            .let(passwordEncoder::encode)

    private fun rawValue(value: String, salt: String) = value.length.toString() + value + salt
}
