package com.montebruni.holder.account.domain.crypto

import com.montebruni.holder.account.domain.crypto.data.Token
import com.montebruni.holder.account.domain.crypto.data.TokenConfiguration

interface TokenProvider {

    fun encode(tokenConfiguration: TokenConfiguration): Token
}
