package com.montebruni.holder.account.domain.port

import com.montebruni.holder.account.domain.entity.User
import java.util.UUID

interface UserRepository {

    fun findById(id: UUID): User?
}
