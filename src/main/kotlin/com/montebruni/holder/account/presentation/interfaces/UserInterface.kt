package com.montebruni.holder.account.presentation.interfaces

import com.montebruni.holder.account.application.usecase.FindUserById
import com.montebruni.holder.account.presentation.interfaces.response.FindUserByIdResponse
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserInterface(
    private val findUserById: FindUserById
) {

    fun findById(id: UUID): FindUserByIdResponse? = findUserById.execute(id)?.let {
        FindUserByIdResponse(
            id = it.id,
            username = it.username.value,
            status = it.status.name
        )
    }
}
