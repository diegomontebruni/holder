package com.montebruni.holder.account.domain.crypto

interface PasswordEncryptor {

    fun encrypt(password: String): String

    fun validate(password: String, encryptedPassword: String): Boolean
}
