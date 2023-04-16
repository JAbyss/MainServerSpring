package com.notmorron.springprofileserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.reactive.function.server.ServerRequest


@SpringBootApplication
class SpringProfileServerApplication

fun main(args: Array<String>) {
    runApplication<SpringProfileServerApplication>(*args)
}