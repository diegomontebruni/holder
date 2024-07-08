package com.montebruni.holder.asset.infrastructure.client

import com.montebruni.holder.asset.application.client.TransactionClient
import com.montebruni.holder.asset.application.client.response.TransactionResponse
import com.montebruni.holder.asset.application.client.response.TransactionResponse.TransactionResponseStatus
import com.montebruni.holder.transaction.presentation.interfaces.TransactionInterface
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TransactionClientAdapter(
    private val transactionInterface: TransactionInterface
) : TransactionClient {

    override fun findById(id: UUID): TransactionResponse? =
        transactionInterface
            .findById(id)
            ?.let { TransactionResponse(status = TransactionResponseStatus.valueOf(it.status)) }
}
