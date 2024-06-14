package com.montebruni.holder.account.domain.valueobject

import com.montebruni.holder.account.domain.constants.PASSWORD_RECOVER_TOKEN_LENGTH
import com.montebruni.holder.account.domain.crypto.EncryptorProvider

data class PasswordRecoverToken(val value: String) {

    init {
        require(value.length == PASSWORD_RECOVER_TOKEN_LENGTH) { "Invalid password recover token" }
    }

    fun validate(token: String): Boolean = value == token

    companion object {

        fun generateRandomToken(encryptor: EncryptorProvider) = PasswordRecoverToken(encryptor.randomToken())
    }
}
