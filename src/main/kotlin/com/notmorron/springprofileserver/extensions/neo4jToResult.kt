package com.notmorron.springprofileserver.extensions

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.buildAndAwait

suspend inline fun <reified T : Any> ServerRequest.neo4jToResult(
    f: (Long, T) -> Result<org.neo4j.driver.Result>
): ServerResponse {
    val idUser = idUser
    val value = bodyToMono<T>().block() ?: return ServerResponse.badRequest().buildAndAwait()

    val result = f(idUser, value)

    return if (result.isSuccess)
        ServerResponse.ok().buildAndAwait()
    else
        ServerResponse.status(500).buildAndAwait()
}