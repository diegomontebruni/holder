package com.montebruni.holder.account.domain.valueobject

import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import java.util.UUID

data class PasswordRecoverToken(val value: String) {

    fun validate(token: String): Boolean = value == token

    companion object {

        fun generateRandomToken(encryptor: EncryptorProvider? = null) =
            encryptor
                ?.let { PasswordRecoverToken(encryptor.randomToken()) }
                ?: UUID.randomUUID().toString().let(::PasswordRecoverToken)
    }
}
