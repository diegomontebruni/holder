package com.montebruni.holder.configuration

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

fun WireMockServer.stubGet(
    url: String,
    responseBody: String,
    responseStatus: Int = HttpStatus.OK.value(),
    contentType: String = MediaType.APPLICATION_JSON_VALUE
) {
    this.stubFor(
        WireMock.get(url)
            .willReturn(
                WireMock.aResponse()
                    .withStatus(responseStatus)
                    .withHeader(HttpHeaders.CONTENT_TYPE, contentType)
                    .withBody(responseBody)
            )
    )
}
