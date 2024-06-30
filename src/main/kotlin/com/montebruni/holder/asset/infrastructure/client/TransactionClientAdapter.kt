package com.montebruni.holder.asset.infrastructure.client

import com.montebruni.holder.asset.application.client.TransactionClient
import com.montebruni.holder.asset.application.client.response.TransactionResponse
import com.montebruni.holder.asset.application.client.response.TransactionResponse.TransactionResponseStatus
import com.montebruni.holder.transaction.domain.repositories.TransactionRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TransactionClientAdapter(
    private val transactionRepository: TransactionRepository
) : TransactionClient {

    override fun findById(id: UUID): TransactionResponse? =
        transactionRepository
            .findById(id)
            ?.let { TransactionResponse(status = TransactionResponseStatus.valueOf(it.status.name)) }
}
