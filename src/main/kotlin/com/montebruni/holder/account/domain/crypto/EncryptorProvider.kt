package com.montebruni.holder.account.domain.crypto

interface EncryptorProvider {

    fun encrypt(value: String): String

    fun validate(value: String, encryptedValue: String): Boolean

    fun randomToken(): String
}
