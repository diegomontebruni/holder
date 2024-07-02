package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import feign.Response
import feign.codec.Decoder
import java.lang.reflect.Type
import kotlin.collections.first
import kotlin.text.contains

class BrapiDecodeResponse(
    private val objectMapper: ObjectMapper
) : Decoder {

    override fun decode(response: Response, type: Type): Any {
        if (contentIsNotJson(response)) {
            return String(response.body().asInputStream().readAllBytes())
        }

        val rootNode = objectMapper.readTree(response.body().asInputStream())

        return objectMapper.convertValue(rootNode.get("results").first(), objectMapper.typeFactory.constructType(type))
    }

    private fun contentIsNotJson(response: Response) = response
        .headers()["Content-Type"]
        ?.first()
        ?.contains("application/json")
        ?.not() == true
}
