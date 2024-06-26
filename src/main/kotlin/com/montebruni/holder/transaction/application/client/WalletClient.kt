package com.montebruni.holder.transaction.application.client

import java.util.UUID

interface WalletClient {

    fun existsByWalletId(walletId: UUID): Boolean
}
