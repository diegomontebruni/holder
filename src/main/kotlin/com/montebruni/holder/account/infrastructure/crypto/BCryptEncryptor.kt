package com.montebruni.holder.account.infrastructure.crypto

import com.montebruni.holder.account.domain.crypto.PasswordEncryptor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.util.Base64

@Component
@Suppress("MagicNumber")
class BCryptEncryptor : PasswordEncryptor {

    private val passwordEncoder = BCryptPasswordEncoder()
    private val random = SecureRandom()

    override fun encrypt(password: String): String {
        val salt = ByteArray(16)
        random.nextBytes(salt)
        val saltString = Base64.getEncoder().encodeToString(salt)
        val encryptedPassword = passwordEncoder.encode(rawPassword(password, saltString))
        return "$saltString:$encryptedPassword"
    }

    override fun validate(password: String, encryptedPasswordWithSalt: String): Boolean {
        val (saltString, encryptedPassword) = encryptedPasswordWithSalt.split(":")
        return passwordEncoder.matches(rawPassword(password, saltString), encryptedPassword)
    }

    private fun rawPassword(password: String, salt: String) = password.length.toString() + password + salt
}
