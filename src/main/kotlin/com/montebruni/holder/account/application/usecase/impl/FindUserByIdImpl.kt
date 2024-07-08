package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.usecase.FindUserById
import com.montebruni.holder.account.application.usecase.output.FindUserByIdOutput
import com.montebruni.holder.account.domain.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FindUserByIdImpl(
    private val userRepository: UserRepository
) : FindUserById {

    override fun execute(id: UUID): FindUserByIdOutput? = userRepository.findById(id)?.let {
        FindUserByIdOutput(
            id = it.id,
            username = it.username,
            status = it.status
        )
    }
}
