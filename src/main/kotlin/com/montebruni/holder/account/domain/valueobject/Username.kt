package com.montebruni.holder.account.domain.valueobject

class Username(val value: String) {

    init {
        runCatching {
            Email(value)
        }.getOrElse {
            throw IllegalArgumentException("The username must be a valid email")
        }
    }
}
