package com.montebruni.holder.asset.application.client.response

data class TransactionResponse(
    val status: TransactionResponseStatus
) {

    enum class TransactionResponseStatus { PENDING, CONFIRMED, FAILED }
}
