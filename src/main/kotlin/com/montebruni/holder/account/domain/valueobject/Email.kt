package com.montebruni.holder.account.domain.valueobject

data class Email(val value: String) {

    init {
        require(isValidEmail()) { "Invalid email" }
    }

    private fun isValidEmail(): Boolean =
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
            .toRegex()
            .matches(value)
}
