package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.response

data class BrapiResponse<T>(
    val results: List<T>
)
