package com.montebruni.holder.asset.application.client

import com.montebruni.holder.asset.application.client.response.TransactionResponse
import java.util.UUID

interface TransactionClient {

    fun findById(id: UUID): TransactionResponse?
}
