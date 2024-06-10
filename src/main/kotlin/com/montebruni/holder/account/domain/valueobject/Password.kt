package com.montebruni.holder.account.domain.valueobject

import com.montebruni.holder.account.domain.crypto.PasswordEncryptor
import java.security.SecureRandom
import kotlin.random.Random

@Suppress("MagicNumber", "UnusedPrivateProperty")
data class Password(val value: String) {

    constructor() : this(randomPassword())

    init {
        if (isEncrypted().not()) {
            require(value.length >= 6 && value.length <= 12) { "Password length should be between 6 and 12" }
            require(value.any { it.isDigit() }) { "Password should contain a digit" }
            require(value.any { it.isLowerCase() }) { "Password should contain a lowercase letter" }
            require(value.any { it.isUpperCase() }) { "Password should contain an uppercase letter" }
            require(value.any { it in "!@#$%^&*()-_=+{}[]|:;<>?,./" }) { "Password should contain a special character" }
        }
    }

    fun encrypt(encryptor: PasswordEncryptor) = this
        .takeIf { isEncrypted() }
        ?: copy(value = encryptor.encrypt(value))

    fun validate(encryptedPassword: String, encryptor: PasswordEncryptor) =
        encryptor.validate(value, encryptedPassword)

    private fun isEncrypted(): Boolean =
        value
            .split(":")
            .let { it.size == 2 && it.first().length == 24 && it.last().length == 60 }

    companion object {

        private fun randomPassword(): String {
            val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()-_=+{}[]|:;<>?,./"
            val passwordLength = 12
            val secureRandom = SecureRandom()
            val random = Random(secureRandom.nextLong())
            val password = StringBuilder(passwordLength)

            password.append(chars.filter { it.isDigit() }.random(random))
            password.append(chars.filter { it.isUpperCase() }.random(random))
            password.append(chars.filter { it.isLowerCase() }.random(random))
            password.append(chars.filter { it in "!@#$%^&*()-_=+{}[]|:;<>?,./" }.random(random))

            for (i in 4 until passwordLength) {
                password.append(chars[random.nextInt(chars.length)])
            }

            return password.toString().toList().shuffled(random).joinToString("")
        }
    }
}
