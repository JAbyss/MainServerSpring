package com.notmorron.springprofileserver.routes.likes

import com.notmorron.springprofileserver.routes.likes.handler.LikesHandler
import org.springframework.web.reactive.function.server.CoRouterFunctionDsl


fun CoRouterFunctionDsl.likesRoute(likesHandler: LikesHandler) = run {

    "/likes".nest {
        GET("/users", likesHandler::getLikes)
        POST("/createCouple", likesHandler::createCouple)
        DELETE("/dismissLike", likesHandler::dismissLike)
    }
}