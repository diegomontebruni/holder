package com.montebruni.holder.transaction.presentation.interfaces

import com.montebruni.holder.transaction.application.usecase.FindById
import com.montebruni.holder.transaction.presentation.interfaces.response.FindByIdResponse
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TransactionInterface(
    private val findById: FindById
) {

    fun findById(id: UUID): FindByIdResponse? = findById.execute(id)?.let {
        FindByIdResponse(
            id = it.id,
            status = it.status.toString(),
            walletId = it.walletId,
            ticker = it.ticker,
            quantity = it.quantity,
            value = it.value.value.toDouble(),
            operation = it.operation.name,
            type = it.type.name,
            description = it.description,
            createdAt = it.createdAt
        )
    }
}
