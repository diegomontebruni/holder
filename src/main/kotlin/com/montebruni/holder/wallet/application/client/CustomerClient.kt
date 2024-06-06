package com.montebruni.holder.wallet.application.client

import com.montebruni.holder.wallet.application.client.response.CustomerResponse
import java.util.UUID

interface CustomerClient {

    fun findById(id: UUID): CustomerResponse?
}
