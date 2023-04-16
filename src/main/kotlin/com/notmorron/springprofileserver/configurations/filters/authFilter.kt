package com.notmorron.springprofileserver.configurations.filters

import com.foggyskies.server.plugin.m
import com.notmorron.springprofileserver.configurations.Redis
import com.notmorron.springprofileserver.configurations.TaskManager
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.reactive.function.server.CoRouterFunctionDsl
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

val CoRouterFunctionDsl.authFilter
    get() = this.filter { serverRequest, suspendFunction1 ->
        val token = serverRequest.headers().asHttpHeaders()["Authorization"]?.first()
            ?: return@filter ServerResponse.badRequest().buildAndAwait()

        val cache = Redis.getValue(token)

        val result = if (cache != null) {
            val map = cache as Map<String, Long>

            if (map["time"]!! > System.currentTimeMillis()) {
                val newRequest = ServerRequest.from(serverRequest).attribute("idUser", map["idUser"]!!).build()
                return@filter suspendFunction1(newRequest)
            } else {
                verifyToken(token)
            }
        } else {
            verifyToken(token)
        }

        result.onSuccess {
            TaskManager.addTask(TaskManager.Task(code = token, 5.m, {
                Redis.setValue(token, mapOf("time" to System.currentTimeMillis() + 5.m, "idUser" to it))
            }){
                Redis.deleteValue(token)
            })
            val newRequest = ServerRequest.from(serverRequest).attribute("idUser", it).build()
            return@filter suspendFunction1(newRequest)
        }.onFailure {
            return@filter ServerResponse.badRequest().buildAndAwait()
        }
        return@filter ServerResponse.badRequest().buildAndAwait()
    }

private fun verifyToken(token: String): Result<Long> {

    val restTemplate = RestTemplate()
    restTemplate.errorHandler = RestTemplateResponseErrorHandler()
    val httpHeaders = HttpHeaders()
    httpHeaders["Authorization"] = token
    httpHeaders.contentType = MediaType.TEXT_PLAIN
    val request = HttpEntity<Any>(httpHeaders)
    val response =
        restTemplate.exchange<Long>("http://0.0.0.0:9291/verifyToken", HttpMethod.GET, request)

    return if (response.statusCode.is2xxSuccessful) {
        response.body ?: Result.failure<Long>(NullPointerException())
        Result.success(response.body!!)
    } else
        Result.failure(Error(response.statusCode.toString()))
}

@Component
class RestTemplateResponseErrorHandler : ResponseErrorHandler {
    override fun hasError(response: ClientHttpResponse): Boolean {
        return false
    }

    override fun handleError(response: ClientHttpResponse) {}
}