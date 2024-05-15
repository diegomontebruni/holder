package com.montebruni.holder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication
@ConfigurationPropertiesScan
@ServletComponentScan
class HolderApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<HolderApplication>(*args)
}
