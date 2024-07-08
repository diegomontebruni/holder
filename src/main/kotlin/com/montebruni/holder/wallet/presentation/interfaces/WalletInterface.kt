package com.montebruni.holder.wallet.presentation.interfaces

import com.montebruni.holder.wallet.application.usecase.GetWalletById
import com.montebruni.holder.wallet.presentation.interfaces.response.GetWalletByIdResponse
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WalletInterface(
    private val getWalletById: GetWalletById
) {

    fun getWalletById(id: UUID): GetWalletByIdResponse? =
        getWalletById.execute(id)
            ?.let {
                GetWalletByIdResponse(
                    id = it.id,
                    managerId = it.managerId,
                    balance = it.balance.value.toDouble(),
                    createdAt = it.createdAt
                )
            }
}
