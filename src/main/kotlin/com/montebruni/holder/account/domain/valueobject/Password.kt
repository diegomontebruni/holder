package com.montebruni.holder.account.domain.valueobject

import java.security.SecureRandom
import kotlin.random.Random

@Suppress("MagicNumber", "UnusedPrivateProperty")
class Password(val value: String) {

    constructor() : this(randomPassword())

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
