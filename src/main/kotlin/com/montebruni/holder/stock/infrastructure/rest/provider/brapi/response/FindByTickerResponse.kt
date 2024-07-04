package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.response

import com.fasterxml.jackson.annotation.JsonProperty

data class FindByTickerResponse(
    @JsonProperty("symbol")
    val ticker: String,
    @JsonProperty("longName")
    val name: String,
    @JsonProperty("regularMarketPrice")
    val price: Double
)
