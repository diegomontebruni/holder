package com.montebruni.holder.transaction.application.usecase.impl

import com.montebruni.holder.transaction.application.client.StockClient
import com.montebruni.holder.transaction.application.client.WalletClient
import com.montebruni.holder.transaction.application.client.exception.StockNotFoundException
import com.montebruni.holder.transaction.application.client.exception.WalletNotFoundException
import com.montebruni.holder.transaction.application.event.EventPublisher
import com.montebruni.holder.transaction.application.event.events.TransactionCreatedEvent
import com.montebruni.holder.transaction.application.usecase.CreateTransaction
import com.montebruni.holder.transaction.application.usecase.input.CreateTransactionInput
import com.montebruni.holder.transaction.application.usecase.input.toTransaction
import com.montebruni.holder.transaction.domain.repositories.TransactionRepository
import org.springframework.stereotype.Service

@Service
class CreateTransactionImpl(
    private val transactionRepository: TransactionRepository,
    private val walletClient: WalletClient,
    private val stockClient: StockClient,
    private val eventPublisher: EventPublisher
) : CreateTransaction {

    override fun execute(input: CreateTransactionInput) {
        walletClient.existsByWalletId(input.walletId)
            .takeIf { it }
            ?: throw WalletNotFoundException()

        stockClient.existsByTicker(input.ticker)
            .takeIf { it }
            ?: throw StockNotFoundException()

        input
            .toTransaction()
            .let(transactionRepository::save)
            .let(::TransactionCreatedEvent)
            .let(eventPublisher::publishEvent)
    }
}
