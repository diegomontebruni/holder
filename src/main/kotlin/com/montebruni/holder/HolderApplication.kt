package com.montebruni.holder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.cloud.openfeign.EnableFeignClients
import java.util.TimeZone

@SpringBootApplication
@ConfigurationPropertiesScan
@ServletComponentScan
@EnableFeignClients
class HolderApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    runApplication<HolderApplication>(*args)
}
