package com.montebruni.holder.account.domain.port

import com.montebruni.holder.account.domain.entity.User
import java.util.UUID

interface UserRepository {

    fun save(user: User): User
    fun findById(id: UUID): User?
    fun findByUsername(username: String): User?
}
