package com.notmorron.springprofileserver.routes.couple

import com.notmorron.springprofileserver.routes.couple.handler.CoupleHandler
import org.springframework.web.reactive.function.server.CoRouterFunctionDsl

fun CoRouterFunctionDsl.coupleRoute(coupleHandler: CoupleHandler) = run {

    "/couple".nest {
        GET("/users", coupleHandler::getCouples)

    }
}