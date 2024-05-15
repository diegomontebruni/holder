package com.montebruni.holder.account.domain.port

import com.montebruni.holder.account.domain.entities.User

interface UserRepository {

    fun findByDocument(document: String): User?
}
